package com.ssm.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.pojo.Blog;

@Controller
@RequestMapping("/test/")
public class TestController {

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("testAjax")
    @ResponseBody
    public String testAjax() {
        return "测试通过";
    }

    /**
     * 1. 谁调用此方法？DispatcherServlet 2. 如何找到的此方法？HandlerMapping 3.
     * 如何调用此方法？通过反射（method.invoke）
     */
    @RequestMapping(value = "doSayHello", method = RequestMethod.GET)
    public ModelAndView doSayHello(String name) {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("message", "Hello World"); // 将数据放到了请求作用域中，视图中的el表达式能够从request作用域中取到数据
        mv.addObject("name", name);
        return mv; // 此值返回给谁？DispatcherServlet
    }
    // DispatcherServlet获取mv之后要做什么？
    // 1.将mv传给视图解析器进行视图解析
    // 2.将model中数据存储到请求作用域中（如果知道数据在哪个域中，视图中直接从指定的域中取数据，效率会高。
    // 不然视图中会从page域一直找到application域中，看看有没有数据，会降低效率）
    // 3.将请求转发到指定的view

    // 限制请求方法，一般从安全角度考虑
    // produces="text/html;charset=utf-8"解决ajax异步请求获取json字符串中的乱码问题
    @GetMapping
    @RequestMapping(value = "getJson", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 123);
        map.put("id", 12);
        map.put("name", "张三");
        return map;
    }

    @PostMapping
    @RequestMapping(value = "addBlog", method = RequestMethod.POST)
    @ResponseBody
    public String addNews(Blog Blog) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(Blog);
        System.out.println(jsonStr);
        return jsonStr;
    }

    // deleteItems?ids=1,3,4；自动将ids以，分隔的参数值转换成数组
    @RequestMapping("deleteItems")
    @ResponseBody
    public String deleteItems(Integer[] ids) {
        return Arrays.toString(ids);
    }

    @RequestMapping("doUpload")
    @ResponseBody
    public String testUploadFile(MultipartFile upfile, HttpServletRequest request) throws Exception {
        String fileName = upfile.getOriginalFilename();
        long size = upfile.getSize();
        System.out.println(fileName + "/" + size);
        String uploadPath = request.getSession().getServletContext().getRealPath("/") + "uploadfiles\\images\\";
        File file = new File(uploadPath + fileName);
        upfile.transferTo(file);

        return fileName;

    }

    @RequestMapping("test")
    public String test(@RequestParam(value = "id", defaultValue = "0", required = false) long parentId) {
        System.out.println(parentId);
        return "";
    }

    @RequestMapping("testDate")
    @ResponseBody
    public String testDate(@DateTimeFormat(pattern = "yyyy-MM-dd") // 默认的格式是yyyy/MM/dd
                                   Date birth) {
        // 如果日期格式不正确，springmvc不会给参数注入值
        return "birth date:" + birth;
    }

    @RequestMapping("doRequestParam03")
    @ResponseBody
    public String doRequestParam03(Integer... ids) {
        // 也可以使用Integer[]数组来接收这种,分割的ID值。例如删除操作的时候，多选时传入的id就是可变的。
        // 现在倾向使用可变参数替换数组
        // 前端传值的时候：ids=1,2,3,4,5

        ObjectMapper objectMapper = new ObjectMapper();

        return "Obtainer ids value is:" + Arrays.toString(ids);
    }

}
