package cn.com.bluemoon.admin.control;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author lsh
 * @date 2018/3/21.
 */
@Controller
public class UEditorConfig {

    @Value(value="classpath:config.json")
    private Resource resource;

    @GetMapping("/ueconfig")
    public void getUEConfig(HttpServletRequest request, HttpServletResponse response){
        Resource res = new ClassPathResource("config.json");
        response.setHeader("Content-Type" , "text/html");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            StringBuffer message=new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null) {
                message.append(line);
            }
            String result = message.toString().replaceAll("/\\*(.|[\\r\\n])*?\\*/","");
            JSONObject json = JSONObject.fromObject(result);
            PrintWriter out = response.getWriter();
            out.print(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
