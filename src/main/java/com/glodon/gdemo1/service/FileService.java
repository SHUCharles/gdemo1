package com.glodon.gdemo1.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bimface.cml.api.beans.TemplateVO;
import com.bimface.cml.client.CMLInternalClient;
import com.glodon.gdemo1.GDemo1Application;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private CMLInternalClient cmlInternalClient = new CMLInternalClient();

    private static String destFileParentPath = GDemo1Application.getDestUrl();
    private static String jsonPath = Materials.getJsonCopyPath();

    public void TexturesUpload() throws FileNotFoundException {
        File file = new File(destFileParentPath);
        File[] files = file.listFiles();
        for (File f :files){
            if (f.isFile()){
                cmlInternalClient.uploadSystemTexture(f.getName(), f.getName(), f.length(), new FileInputStream(f));
            }
        }
    }

    public void JsonUpload() throws IOException {

        List<TemplateVO> templates = cmlInternalClient.getTemplates();
        Map<String, TemplateVO> resultsMap = new HashMap();
        for (TemplateVO template: templates) {
            resultsMap.put(template.getName(), template);
        }
        File file = FileUtils.getFile(jsonPath);
        String content= FileUtils.readFileToString(file,"GB2312");
        JSONObject jobj = JSON.parseObject(content);
        JSONArray instances = jobj.getJSONArray("instances");
        for (int i = 0; i < instances.size(); i++){
            Object instance = instances.get(i);
            if (instance instanceof JSONObject){
                TemplateVO templateVO =  resultsMap.get(((JSONObject) instance).getString("template"));
               if(templateVO != null){
                   cmlInternalClient.createSystemMaterial(((JSONObject) instance).getString("id"),
                           ((JSONObject) instance).getString("name"),templateVO.getId(),
                            ((JSONObject) instance).toJSONString(),"");
               }
               else{
                   System.out.println("template not exists");
               }
            }
        }
    }
}
