package aibili.ronaldo.controller;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import aibili.ronaldo.dao.impl.RestDaoImpl;
import aibili.ronaldo.utils.MD5Util;
import aibili.ronaldo.utils.MimeTypeUtil;
import aibili.ronaldo.utils.ReturnValueUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import eu.medsea.mimeutil.MimeUtil;
//import net.sf.jmimemagic.Magic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
//import net.sf.jmimemagic.MagicMatch;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static sun.rmi.transport.TransportConstants.Magic;

/**
 * Created by rtf on  2018/1/30.
 */

@RestController
@RequestMapping(value = "/api/ronaldo")
public class BaseRestController {
    @Autowired
    private RestDaoImpl restDao;

    @RequestMapping(value = "/*/{id}", method = RequestMethod.GET)
    public Object details(HttpServletRequest request, @PathVariable("id") Integer id) {
        String[] params = urlProcess(request);
        Map<String, Object> result = restDao.findObjectById(params[0], id);
        return ReturnValueUtil.ok(result);
    }

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public Object list(HttpServletRequest request) {
        String[] preUrl = urlProcess(request);
        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry :  request.getParameterMap().entrySet()){
            params.put(entry.getKey(), entry.getValue()[0]);
        }
        if(null !=  params.get("page")){
            Integer pageSize = (null != params.get("pagesize"))?
                    Integer.parseInt(params.get("pagesize").toString()):10;
            params.remove("page");
            params.remove("pagesize");
            PageHelper.startPage(Integer.parseInt(request.getParameterMap().get("page")[0].toString()), pageSize);
            List<Map<String, Object >> list = restDao.findObject(preUrl[0], params);
            PageInfo<Map<String, Object >> pageInfo = new PageInfo(list);
            return ReturnValueUtil.ok(pageInfo);
        }
        List<Map<String, Object >> list = restDao.findObject(preUrl[0], params);
        return ReturnValueUtil.ok(list);
    }

    @RequestMapping(value = "/*", method = RequestMethod.POST)
    public Object create(HttpServletRequest request, @RequestBody Object object) {
        String[] params = urlProcess(request);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(object, Map.class);
        map.put("password", MD5Util.encode(map.get("password").toString()));
        restDao.insert(params[0], map);
        return ReturnValueUtil.ok();
    }

    @RequestMapping(value = "/*/{id}", method = RequestMethod.PUT)
    public Object modify(HttpServletRequest request, @PathVariable("id") Integer id, @RequestBody Object object) {
        String[] params = urlProcess(request);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(object, Map.class);
        map.put("password", MD5Util.encode(map.get("password").toString()));
        restDao.update(params[0], id, map);
        return ReturnValueUtil.ok();
    }

    @RequestMapping(value = "/*/{id}", method = RequestMethod.DELETE)
    public Object delete(HttpServletRequest request, @PathVariable("id") Integer id) {
        String[] params = urlProcess(request);
        restDao.delete(params[0], id);
        return ReturnValueUtil.ok();
    }



    private String[] urlProcess(HttpServletRequest request) {
        String prefix = "api/ronaldo/";
        String url = request.getRequestURI();
        return url.substring(url.indexOf(prefix) + prefix.length()).split("/");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(HttpServletRequest request,
                         @RequestParam("files") MultipartFile[] uploadfiles ) {
        String filePath="/home/fungleo/yjl/tmp/";
        if(!new File(filePath).exists()){
            new File(filePath).mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(new Date())+"/";
        if(!new File(filePath+dateNowStr).exists()){
            new File(filePath+dateNowStr).mkdirs();
        }
        MultipartFile file;
        BufferedOutputStream stream;
        for (int i= 0; i < uploadfiles.length; i++) {
            file =uploadfiles[i];
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String suffix = MimeTypeUtil.getSuffix(file.getContentType());
                    if(null == suffix) {
                        return ReturnValueUtil.fail("不支持的文件类型！");
                    }
                    stream = new BufferedOutputStream(new FileOutputStream(new File((filePath+dateNowStr+new Date().getTime()+"."+suffix))));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    return ReturnValueUtil.fail("You failed to upload " + i + " => " + e.getMessage());
                }
            } else {
                return ReturnValueUtil.fail("You failed to upload " + i + " because the file was empty.");
            }
        }
        return ReturnValueUtil.ok();
    }

    private String tableName(String tableName){
        Map<String, Object> map = restDao.findTableView(tableName);
        if(null != map && map.size()>0){
            tableName = tableName + "_view";
        }
        return tableName;
    }
}
