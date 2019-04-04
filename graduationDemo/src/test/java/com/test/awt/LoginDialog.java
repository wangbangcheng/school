package com.test.awt;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import org.apache.taglibs.standard.tag.common.core.OutSupport;
import org.apache.tomcat.jni.OS;

import com.alibaba.druid.util.StringUtils;
import com.test.awt.ImageScale;
import com.wbc.graduation.exception.Md5EncodeException;
import com.wbc.graduation.util.MD5diyUtils;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private final JPanel contentPanel_main = new JPanel();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    //登录窗体宽高及扩展高
    private static final int DIALOG_WIDTH=374;
    private static final int DIALOG_HEIGHT=290;
    private static final int DIALOG_HEIGHT_EXTEND=540;
    
    //host和端口
    private static final int port = 10001;
    private static final int port_file = 10010;
    private static final String hostIP = "127.0.0.1";
    //登录/注册
    private Socket client_socket;
    private InputStream client_is;
    private PrintWriter pw;
    private OutputStream client_os;
    private BufferedReader br;
    private int length;
    //文件传输
    private Socket client_socket_file;
    private InputStream client_is_file;
    private OutputStream client_os_file;
    
    //工具类
    
    
	private void connectServer(int port) {
		// TODO Auto-generated method stub	
		try {
			System.out.println("登录验证socket开启...");
			client_socket = new Socket();
			client_socket.connect(new InetSocketAddress(hostIP, port),10 * 1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**提交登录/注册信息给服务端
	 * @param user			用户名
	 * @param password		密码
	 * @param flag			1-登录/2-注册
	 * @param  String 		flag|user|password
	 * @return String		1|s|登录成功
	 * 						1|f|登录失败
	 * 						2|s|注册成功
	 * 						2|f|注册失败
	 * 						3|异常输入
	 */
	private String transmitData(String username,String password,int flag){
		String request = "";
		String response = "";
		MD5diyUtils md5 = new MD5diyUtils();
		try {
			if(flag == 1){
				request = "1|"+ username + "|"+md5.encrypt16(password);
			}else if(flag == 2){
				request = "2|"+ username + "|"+md5.encrypt16(password);
			}else{
				return "3|非法输入格式";
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
    	connectServer(port);
    	setTitle("客户端登录/验证");
        setAlwaysOnTop(true);
        setResizable(false);
        setBounds(400, 150, DIALOG_WIDTH,DIALOG_HEIGHT);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        //设置居中
        //setLocation(WindowXY.getXY(LoginDialog.this.getSize()));

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
				if(!StringUtils.isEmpty(textField.getText())&&!StringUtils.isEmpty(textField_1.getText())){
					
					String response = transmitData(textField.getText(),textField_1.getText(),1);
					JFrame main = new JFrame();
					main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口将退出程序
					main.setBounds(400,150,500,360);
		            main.setVisible(true);
		            main.setTitle("传输界面");
		            main.setContentPane(contentPanel_main);
		            JFileChooser Jfile = new JFileChooser();
		            Jfile.setBorder(new TitledBorder(null, "传输文件", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		            contentPanel_main.add(Jfile);
		
		            
		            LoginDialog.this.setVisible(false);
					
				}
			}
        	
        	
        });
        contentPanel.add(btnNewButton_1);

        textField = new JTextField();
        textField.setBounds(133, 147, 150, 25);
        contentPanel.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(133, 182, 150, 25);
        contentPanel.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel = new JLabel("用户名");
        lblNewLabel.setBounds(53, 151, 54, 15);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("密 码");
        lblNewLabel_1.setBounds(53, 194, 54, 15);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("登录");     
        lblNewLabel_2.setBounds(5, 0, 360, 136);
        //TODO
        ImageIcon icon=new ImageIcon(LoginDialog.class.getResource("timg.jpg"));
        icon=ImageScale.getImage(icon, lblNewLabel_2.getWidth(), lblNewLabel_2.getHeight());
        lblNewLabel_2.setIcon((icon));
        contentPanel.add(lblNewLabel_2);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u6CE8\u518C\u7528\u6237", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(12, 259, 336, 221);
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_3 = new JLabel("用户名");
        lblNewLabel_3.setBounds(41, 29, 55, 18);
        panel.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("验证码");
        lblNewLabel_4.setBounds(41, 85, 55, 18);
        panel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("密 码");
        lblNewLabel_5.setBounds(41, 115, 55, 18);
        panel.add(lblNewLabel_5);

        JLabel label = new JLabel("确认密码");
        label.setBounds(41, 145, 55, 18);
        panel.add(label);

        textField_2 = new JTextField();
        textField_2.setBounds(123, 22, 150, 25);
        panel.add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(123, 80, 150, 25);
        panel.add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(123, 113, 150, 25);
        panel.add(textField_4);
        textField_4.setColumns(10);

        textField_5 = new JTextField();
        textField_5.setBounds(123, 145, 150, 25);
        panel.add(textField_5);
        textField_5.setColumns(10);

        JButton btnNewButton_2 = new JButton("发送验证码");
        btnNewButton_2.setBounds(123, 52, 83, 23);
        panel.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("取 消");
        btnNewButton_3.setBounds(51, 182, 83, 27);
        panel.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("确 认");
        btnNewButton_4.setBounds(190, 182, 83, 27);
        panel.add(btnNewButton_4);
    }


}


