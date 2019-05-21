package com.test.awt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;





import com.wbc.graduation.util.DesUtil;
import com.wbc.graduation.util.SecretKey;

public class SendFileFrame extends JFrame{

	JPanel contentPanel_1 = new JPanel();
	
	private static final int JFrame_WIDTH=400;
    private static final int JFrame_HEIGHT=100;
	
    private String filePath;
    private File file;
    //预加载路径
    private static final String pre_path = "D:\\testDemo";
    
    private JFileChooser Jfile;
    
    private static final int port_file = 10010;
    private static final String hostIP = "127.0.0.1";
    
    private Socket client_socket_file;
    private InputStream client_is_file;
    private DataOutputStream client_os_file;
    
    
    public static void main(String[] args) {
    	SendFileFrame main = new SendFileFrame("123");
    	main.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	main.setVisible(true);
	}
    
    //请求连接服务端
   	public void connectServer(int port) {
   		// TODO Auto-generated method stub	
   		try {
   			System.out.println("请求连接服务端（传输文件端口）");
   			client_socket_file = new Socket();
   			client_socket_file.connect(new InetSocketAddress(hostIP, port),10 * 1000);
   			client_os_file = new DataOutputStream(client_socket_file.getOutputStream());

   			System.out.println("请求成功（传输文件端口）");
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   	}
   	//关闭socket
   	public void closeConnect() {
   		if(client_socket_file != null) {
   			try {
   				client_socket_file.close();
   				System.out.println("成功关闭socket");
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
    
    public SendFileFrame(String username) {
    	connectServer(port_file);
    	setTitle("用户主页面");
        setAlwaysOnTop(true);
        setResizable(false);
        setBounds(400, 150, JFrame_WIDTH,JFrame_HEIGHT);
        getContentPane().setLayout(new BorderLayout());
        setContentPane(contentPanel_1);
        contentPanel_1.setBorder(new TitledBorder(null, "用户："+username, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
        Jfile = new JFileChooser(pre_path);
        Jfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        Jfile.setBorder(new TitledBorder(null, "传输文件", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//	    Jfile.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
	    
	    
	    Jfile.setVisible(false);
	    contentPanel_1.add(Jfile);
	    
	    
        
        JButton sendFileBtn = new JButton("传输文件");
        sendFileBtn.addActionListener(new ActionListener() {
           
        	public void actionPerformed(ActionEvent arg0) {
        		SendFileFrame.this.setVisible(false);
        		Jfile.setVisible(true);
        		int result = Jfile.showDialog(new JLabel(), "选择");
        	    if(result == Jfile.APPROVE_OPTION) {
        	    	RefreshSocket(port_file);	//请求连接服务
        	    	
        	    	file = Jfile.getSelectedFile();
        	    	filePath = file.getAbsolutePath();
        	    	System.out.println(filePath);
                    
        	    	try {
        	   			client_is_file = new FileInputStream(filePath);
        				
						if(client_is_file != null ) {
							byte[] buffer = new byte[client_is_file.available()];
							client_is_file.read(buffer);
							//读取密钥文件key.properties
				    		SecretKey key = new SecretKey();
				        	String secretKey = key.get();
				        	System.out.println("加密密钥:"+ secretKey);
				        	
							DesUtil desUtil = new DesUtil(secretKey, buffer);
							byte[] buffer_encrypt = desUtil.deal(buffer, 1);
							
							if(buffer_encrypt != null) {
								System.out.println("加密成功");
								client_os_file.writeUTF(file.getName());
								client_os_file.writeInt(buffer.length);
								System.out.println("待加密数据长度："+buffer.length);
								
								client_os_file.write(buffer_encrypt, 0, buffer_encrypt.length);
								
								client_os_file.flush();
				                System.out.println("加密文件已传输");
							}
							//关闭资源
							if(client_is_file != null) {
								client_is_file.close();
							}
							if(client_os_file != null) {
								client_os_file.close();
							}
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						SendFileFrame.this.setVisible(true);
					}

        	    }
        	    
            }
        });
        contentPanel_1.add(sendFileBtn);
	}
    //退出登录/关闭窗口事件  关闭文件监听端口
    public void exitLoginEvent(){
    	try {
    		RefreshSocket(port_file);
			client_os_file.writeUTF("退出登录");
			if(client_os_file!=null){
				client_os_file.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
    	SendFileFrame.this.dispose();
	}
    //窗口关闭事件
	
}
