package cn.com.bluemoon.admin.control;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.bluemoon.admin.domain.vo.UploadFileVo;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.bluemoon.fastdfs.dubbo.BaseFileService;
import com.bluemoon.fastdfs.dubbo.result.UploadResult;
import com.google.common.collect.Maps;

/**
 * Created by lsh on 2017/8/15.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Reference(version="1.0.0", timeout=60000)
    private BaseFileService baseFileService ;
    @Value("${picPath}")
    private String qa_manage_url;

    @PostMapping("/ue")
    public String ueUploadFile(HttpServletRequest request, @RequestParam("upfile") MultipartFile multipartFile) throws IOException {
        try {
            Map<String,String> map = Maps.newHashMap();
            UploadResult uploadResult = baseFileService.upload(multipartFile.getBytes(),multipartFile.getOriginalFilename(),"uEditor",map);
            if (uploadResult != null) {
                UploadFileVo uploadFileVo  = new UploadFileVo();
                uploadFileVo.setOriginal(multipartFile.getOriginalFilename());
                uploadFileVo.setState("SUCCESS");
                uploadFileVo.setSize(multipartFile.getSize()+"");
                uploadFileVo.setTitle(multipartFile.getName());
                uploadFileVo.setUrl(qa_manage_url + uploadResult.getGroup() + "/" + uploadResult.getFileName());
                return JSON.json(uploadFileVo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    @RequestMapping(value = "/img",method = RequestMethod.POST)
    public String imgUploadFile(HttpServletRequest request) throws IOException {
        try {
        	
        	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        	MultipartFile file = multipartRequest.getFile("errPic");
        	byte[] fileByte = file.getBytes();
        	
//        	String fileData = request.getParameter("base64");
        	String fileName = file.getOriginalFilename();

//            String data = "";
//            if(fileData == null || "".equals(fileData)){
//                throw new Exception("上传失败，上传图片数据为空");
//            }else{
//                String [] d = fileData.split("base64,");
//                if(d != null && d.length == 2){
//                    data = d[1];
//                }else{
//                    throw new Exception("上传失败，数据不合法");
//                }
//            }
//
//            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
//            byte[] fileByte = Base64Utils.decodeFromString(data);
            System.out.println(fileByte.length);

//            byte[] fileByte = Base64.decodeBase64(fileData);
            UploadResult uploadResult = baseFileService.upload(fileByte, fileName, "b2b", null);
            if (uploadResult != null) {
                String path = qa_manage_url + uploadResult.getGroup() + "/" + uploadResult.getFileName();
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/pic",method = RequestMethod.POST)
    public String picUploadFile(HttpServletRequest request) throws IOException {
        try {
        	String fileData = request.getParameter("base64");
        	String fileName = request.getParameter("fileName");

            String data = "";
            if(fileData == null || "".equals(fileData)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                String [] d = fileData.split("base64,");
                if(d != null && d.length == 2){
                    data = d[1];
                }else{
                    throw new Exception("上传失败，数据不合法");
                }
            }

            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] fileByte = Base64Utils.decodeFromString(data);
            System.out.println(fileByte.length);

//            byte[] fileByte = Base64.decodeBase64(fileData);
            UploadResult uploadResult = baseFileService.upload(fileByte, fileName, "b2b", null);
            if (uploadResult != null) {
                String path = qa_manage_url + uploadResult.getGroup() + "/" + uploadResult.getFileName();
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
