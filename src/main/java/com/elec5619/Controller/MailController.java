package com.elec5619.Controller;

import com.alibaba.fastjson.JSONObject;
import com.elec5619.Entity.Mail;
import com.elec5619.Entity.User;
import com.elec5619.Repository.UserRepository;
import com.elec5619.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;

import java.util.Random;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    // Send the email to user
    @Async
    @PostMapping("post")
    public String postMail(@RequestBody JSONObject Userinfo) {
        Mail mail = new Mail();
        mail.setTo(Userinfo.getString("email"));
        mail.setSubject("Welcome To Together");
        mail.setContent("Welcome to Together: " + Userinfo.getString("firstname") +" " + Userinfo.getString("lastname"));
        mailService.sendMail(mail);
        return "Send Successfully";
    }

    // when user forget the password, the system will send the new password to customer via email
    @Async
    @GetMapping(value="/forget/{username}")
    public boolean forget(@PathVariable("username") String username) throws Exception{
        System.out.println(username);
        User user = userRepository.findByEmail(username);
        if(user == null){
            return false;
        }else{
            Mail mail = new Mail();
            mail.setTo(username);
            String val = "";
            Random random = new Random();
            int numbers = 8; // 如果想要7-16位: random.nextInt(9) + 7;
            for (int i = 0; i < numbers; i++) {

                // output letter or integer?
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                // string
                if ("char".equalsIgnoreCase(charOrNum)) {

                    //get Upper or lower letter
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char) (choice + random.nextInt(26));
                } else if ("num".equalsIgnoreCase(charOrNum)) {
                    // number
                    val += String.valueOf(random.nextInt(10));
                }
            }
            mail.setTo(username);
            mail.setSubject("Forget your password?");
            mail.setContent("Hi, " + user.getFirstname() +" " + user.getLastname()+". Your new password is " + val +".");
            mailService.sendMail(mail);
            String md5_password = md5_32Encrypt(val);
            user.setPassword(md5_password);
            System.out.println(md5_password);
            userRepository.save(user);
            return true;
        }
    }

    public static String md5_32Encrypt(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


}
