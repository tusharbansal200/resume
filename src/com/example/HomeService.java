package com.example;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;

import com.example.bean.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/home")
public class HomeService {
	@GET
	@Path("/download")
	@Produces("application/msword")
	public Response getFile(@Context HttpServletResponse response) {
 ClassLoader classLoader=getClass().getClassLoader();
 File file=new File(classLoader.getResource("tushar_resume.doc").getFile());
		//File file = new File("src/tushar_resume.doc");
 		byte[] pdfContent1;
		try {
			pdfContent1 = FileUtils.readFileToByteArray(file);
			//ResponseBuilder response = Response.ok((Object) file);
   			response.setContentType("application/msword");
			response.setHeader("Content-Disposition","attachment; filename=\"tushar_resume.doc\"");
			//return response.build();
			OutputStream out1 = (OutputStream) response.getOutputStream();
			out1.write(pdfContent1);
			out1.flush();
			out1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@POST
	@Path("sendMail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMail(String argument){
		System.out.println(argument);
		User user=new Gson().fromJson(argument, User.class);
		System.out.println(user.getName());
		System.out.println(user.getEmail());
		try{
		Properties props = new Properties();
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
		"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.port","465");
		Session session = Session.getInstance(props,
		new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("enggtusharbnsl@gmail.com","Tush@r211");
		}
		});
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("enggtusharbnsl@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,
		InternetAddress.parse("tushar.bansal200@gmail.com"));
		/*message.setRecipients(Message.RecipientType.CC,
				InternetAddress.parse(user.getEmail()));
		message.setRecipients(Message.RecipientType.BCC,
				InternetAddress.parse("tushar.bansal200@gmail.com"));*/
		message.setSubject("Send from website");
		String text="message: "+user.getMessage()+"<br> Name : "+user.getName()+"<br> contact number :"+user.getPhone()+"<br> mail :"+user.getEmail();
		message.setContent(text, "text/html");
		Transport.send(message);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
