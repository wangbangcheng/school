package com.test.awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel;

import com.alibaba.druid.util.StringUtils;
import com.test.awt.ImageScale;
import com.wbc.graduation.exception.Md5EncodeException;
import com.wbc.graduation.util.MD5diyUtils;
import com.wbc.graduation.util.SecretKey;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JPasswordField passwordField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JPasswordField passwordField_2;
    private JPasswordField passwordField_3;
    //登录窗体宽高及扩展高
    private static final int DIALOG_WIDTH=374;
    private static final int DIALOG_HEIGHT=290;
    private static final int DIALOG_HEIGHT_EXTEND=560;
    
    //host和端口
    private static final int port = 10011;
    private static final String hostIP = "127.0.0.1";
    //登录/注册
    private Socket client_socket;
    private InputStream client_is;
    private PrintWriter pw;
    private OutputStream client_os;
    private BufferedReader br;


    //请求连接服务端
	private void connectServer(int port) {
		// TODO Auto-generated method stub	
		try {
			System.out.println("请求连接服务端");
			client_socket = new Socket();
			client_socket.connect(new InetSocketAddress(hostIP, port),10 * 1000);
			System.out.println("请求成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//关闭socket
	private void closeConnect() {
		if(client_socket != null) {
			try {
				client_socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//刷新连接
	private void RefreshSocket(int port) {
		closeConnect();
		connectServer(port);
	}
	
	/**提交登录/注册信息给服务端
	 * @param user			用户名
	 * @param password		密码
	 * @param flag			1-登录/2-注册
	 * @param secretKey		本地密钥
	 * @param  String 		flag|user|password|secretKey
	 * @return String		1|s|登录成功
	 * 						1|f|登录失败
	 * 						2|s|注册成功
	 * 						2|f|注册失败
	 * 						3||异常输入
	 * 						4||重置监听
	 */
	private String transmitData(String username,String password,int flag){
		String request = "";
		String response = "";
		MD5diyUtils md5 = new MD5diyUtils();
		try {
			//读取密钥文件key.properties
    		SecretKey key = new SecretKey();
        	String secretKey = key.get();
			if(flag == 1){
				request = "1|"+ username + "|"+md5.encrypt16(password)+"|"+secretKey;
			}else if(flag == 2){
				request = "2|"+ username + "|"+md5.encrypt16(password)+"|"+secretKey;
			}else{
				return "3||非法输入格式";
			}
			System.out.println(request);
			//输入/输出流
			client_os=client_socket.getOutputStream();
			pw=new PrintWriter(client_os);
			client_is=client_socket.getInputStream();
            br=new BufferedReader(new InputStreamReader(client_is));
			//发送登录/注册信息
            pw.write(request);
            pw.flush();
            client_socket.shutdownOutput();
            //接收信息
            String rece = null ;
            while (!((rece = br.readLine())==null)){
            	System.out.println("接收信息如下:"+rece);
            	response += rece;
            }
            //关闭资源
            br.close();
            client_is.close();
            pw.close();
            client_os.close();
            client_socket.close();
            
            return response;
		} catch (Md5EncodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		
	}
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        try {
            LoginDialog dialog = new LoginDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
          
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public LoginDialog() {
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    	connectServer(port);
    	setTitle("客户端登录/验证");
        setAlwaysOnTop(true);
        setResizable(false);
        setBounds(400, 150, DIALOG_WIDTH,DIALOG_HEIGHT);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);


        JButton btnNewButton = new JButton("注 册");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(LoginDialog.this.getHeight()==DIALOG_HEIGHT_EXTEND){
                    LoginDialog.this.setSize(DIALOG_WIDTH,DIALOG_HEIGHT);
                }
                else{
                    LoginDialog.this.setSize(DIALOG_WIDTH,DIALOG_HEIGHT_EXTEND);
                }
            }
        });
        btnNewButton.setBounds(53, 224, 93, 23);
        contentPanel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("登 录");
        btnNewButton_1.setBounds(190, 224, 93, 23);
        btnNewButton_1.addActionListener(new ActionListener() {

        	//登录成功后切换成传输文件页面
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 登录事件
				if(!StringUtils.isEmpty(textField.getText())&&!StringUtils.isEmpty(passwordField_1.getText())){
					
					String response = transmitData(textField.getText(),passwordField_1.getText(),1);
					System.out.println(response);
					String[] resp_arr = response.split("\\|");
					if("s".equals(resp_arr[1])) {
						SendFileFrame sendFile = new SendFileFrame(textField.getText());
						sendFile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						sendFile.setVisible(true);
						JButton exitLoginBtn = new JButton("退出登录");
						sendFile.contentPanel_1.add(exitLoginBtn);
						exitLoginBtn.addActionListener(new ActionListener() {
				            public void actionPerformed(ActionEvent arg0) {
				            	sendFile.exitLoginEvent();
				            	LoginDialog.this.RefreshSocket(port);
				            	LoginDialog.this.setVisible(true);
				            }
						});
						//关闭窗口事件-重置监听
						sendFile.addWindowListener(new WindowAdapter() {
							 
							 
							public void windowClosing(WindowEvent e) {
								sendFile.exitLoginEvent();
								super.windowClosing(e);
								LoginDialog.this.RefreshSocket(port);
								LoginDialog.this.setVisible(true);
							}
						
						});
			            //原页面隐藏
			            LoginDialog.this.setVisible(false);
					}else {
						JFrame warning = new JFrame();
						warning.setBounds(400, 150, DIALOG_WIDTH,DIALOG_HEIGHT);
						warning.setVisible(true);
						warning.setTitle("登录失败");
						warning.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			            JPanel Panel_warning = new JPanel();
			            warning.setContentPane(Panel_warning);
			            JLabel label_warning = new JLabel(resp_arr[2]);
			            Panel_warning.add(label_warning);
			            
			            RefreshSocket(port);
			            warning.addWindowListener(new WindowAdapter() {
			            	 
			            	 
			            	public void windowClosing(WindowEvent e) {
				            	super.windowClosing(e);
				            	//加入动作
				            	LoginDialog.this.setVisible(true);
				            	//
			            	 }
			            	 
			            });
					}
					
					
					
				}
			}
        	
        	
        });
        contentPanel.add(btnNewButton_1);

        textField = new JTextField();
        textField.setBounds(133, 147, 150, 25);
        contentPanel.add(textField);
        textField.setColumns(10);

//        textField_1 = new JTextField();
//        textField_1.setBounds(133, 182, 150, 25);
//        contentPanel.add(textField_1);
//        textField_1.setColumns(10);
        
        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(133, 182, 150, 25);
        contentPanel.add(passwordField_1);
        passwordField_1.setColumns(10);

        JLabel lblNewLabel = new JLabel("用户名");
        lblNewLabel.setBounds(53, 151, 54, 15);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("密 码");
        lblNewLabel_1.setBounds(53, 194, 54, 15);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("登录");     
        lblNewLabel_2.setBounds(5, 0, 360, 136);
        //TODO
        //登录主页面插图
        String path = LoginDialog.class.getResource("\\").getPath();
        System.out.println(path);
        path = path.substring(0,path.lastIndexOf("/")) + "/timg.jpg";
        
        System.out.println(path);
        ImageIcon icon=new ImageIcon(path);
        icon=ImageScale.getImage(icon, lblNewLabel_2.getWidth(), lblNewLabel_2.getHeight());
        lblNewLabel_2.setIcon((icon));
        contentPanel.add(lblNewLabel_2);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u6CE8\u518C\u7528\u6237", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(12, 259, 336, 240);
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("用户名");
        lblNewLabel_3.setBounds(41, 29, 55, 18);
        panel.add(lblNewLabel_3);

//        JLabel lblNewLabel_4 = new JLabel("验证码");
//        lblNewLabel_4.setBounds(41, 85, 55, 18);
//        panel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("密 码");
        lblNewLabel_5.setBounds(41, 80, 55, 18);
        panel.add(lblNewLabel_5);

        JLabel label = new JLabel("确认密码");
        label.setBounds(41, 119, 55, 18);
        panel.add(label);

        //用户名
        textField_2 = new JTextField();
        textField_2.setBounds(123, 22, 150, 25);
        panel.add(textField_2);
        textField_2.setColumns(10);
        //验证码
//        textField_3 = new JTextField();
//        textField_3.setBounds(123, 80, 150, 25);
//        panel.add(textField_3);
//        textField_3.setColumns(10);
        //密码
        passwordField_2 = new JPasswordField();
        passwordField_2.setBounds(123, 80, 150, 25);
        panel.add(passwordField_2);
        passwordField_2.setColumns(10);
        //确认密码
        passwordField_3 = new JPasswordField();
        passwordField_3.setBounds(123, 119, 150, 25);
        panel.add(passwordField_3);
        passwordField_3.setColumns(10);
        //密码不一致
        JLabel diff_warning = new JLabel("密码不一致");
        diff_warning.setBounds(123, 145, 150, 25);
        diff_warning.setForeground(Color.red);
        diff_warning.setVisible(false);
        panel.add(diff_warning);
        
        
      
        JButton btnNewButton_3 = new JButton("取 消");
        btnNewButton_3.setBounds(51, 200, 83, 27);
        panel.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("确 认");
        btnNewButton_4.setBounds(190, 200, 83, 27);
        panel.add(btnNewButton_4);
        btnNewButton_4.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 注册事件
				System.out.println("注册事件");
				System.out.println(textField_2.getText());
				System.out.println(passwordField_3.getText());
				System.out.println(passwordField_3.getText());
				diff_warning.setVisible(false);
				//用户名、密码、确认密码不为空
				if(!StringUtils.isEmpty(textField_2.getText())&&!StringUtils.isEmpty(passwordField_2.getText())&&!StringUtils.isEmpty(passwordField_3.getText())) {
					if(passwordField_3.getText().equals(passwordField_2.getText())) {
						String response = transmitData(textField_2.getText(),passwordField_2.getText(),2);
						String[] resp_arr = response.split("\\|");
						//注册成功 弹出时隐藏主窗口，退出时显示主窗口
						if("s".equals(resp_arr[1])) {
							LoginDialog.this.setVisible(false);
							JFrame warning = new JFrame();
							warning.setBounds(400, 150, DIALOG_WIDTH,DIALOG_HEIGHT);
							warning.setVisible(true);
							warning.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							warning.setTitle("注册成功");
				            JPanel Panel_warning = new JPanel();
				            warning.setContentPane(Panel_warning);
				            JLabel label_warning = new JLabel(resp_arr[2]);
				            Panel_warning.add(label_warning);
				            
				            warning.addWindowListener(new WindowAdapter() {
				            	 
				            	 
				            	public void windowClosing(WindowEvent e) {
					            	super.windowClosing(e);
					            	//加入动作
					            	LoginDialog.this.setVisible(true);
					            	//
				            	 }
				            	 
				            });
						}
						//注册失败
						else {
							LoginDialog.this.setVisible(false);
							JFrame warning = new JFrame();
							warning.setBounds(400, 150, DIALOG_WIDTH,DIALOG_HEIGHT);
							warning.setVisible(true);
							warning.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							warning.setTitle("注册失败");
				            JPanel Panel_warning = new JPanel();
				            warning.setContentPane(Panel_warning);
				            JLabel label_warning = new JLabel(resp_arr[2]);
				            Panel_warning.add(label_warning);
				            
				            warning.addWindowListener(new WindowAdapter() {
				            	 
				            	 
				            	public void windowClosing(WindowEvent e) {
					            	super.windowClosing(e);
					            	//加入动作
					            	LoginDialog.this.setVisible(true);
					            	//
				            	 }
				            	 
				            });
				            
						}
						
					}else {
						System.out.println("密码不一致");
						diff_warning.setVisible(true);
					}
					
				}
				RefreshSocket(port);
			}
        	
        });
    }


}


