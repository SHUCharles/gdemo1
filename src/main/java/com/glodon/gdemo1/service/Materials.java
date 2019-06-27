package com.glodon.gdemo1.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glodon.gdemo1.GDemo1Application;
import com.glodon.gdemo1.utils.SHA1;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;

@Service
public class Materials {
    @Autowired
    private SHA1 sha;
    private static String destFileParentPath = GDemo1Application.destUrl;
    private static String sourceFileParentPath = GDemo1Application.sourceUrl;
    private static String targetPath = GDemo1Application.targetUrl;

    public void copyAndRename() throws IOException {
        File file = new File(targetPath);
        String content = FileUtils.readFileToString(file, "GB2312");
        JSONObject jobj = JSON.parseObject(content);
        JSONArray instances = jobj.getJSONArray("instances");

        for (int i = 0; i < instances.size(); i++) {
            JSONObject instance = (JSONObject) instances.get(i);
            for (Map.Entry<String, Object> entry : instance.entrySet()){
                String key = entry.getKey();
                Object obj = instance.get(key);
                if (obj instanceof Map){
                    if (((Map) obj).containsKey("texture_file")){
                        String ss = (String)((Map) obj).get("texture_file");
                        if (ss.length()!=0){
                            String filePath = sourceFileParentPath + ss;
                            File file1 = new File(filePath);
                            String sha1 = sha.getFileSha1(file1);
                            FileUtils.copyFile(file1,new File(destFileParentPath +sha1));
                            ((Map) obj).put("texture_file",sha1);
                        }
                    }
                }
            }
        }
      FileUtils.writeStringToFile(new File("H:\\SystemMaterials.mlib"), jobj.toJSONString(), Charset.defaultCharset());
    }
}
