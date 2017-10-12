package com.atguigu.datingsite.controller;

import com.atguigu.datingsite.bean.TUser;
import com.atguigu.datingsite.service.TUserService;
import com.atguigu.datingsite.util.FastDFSUtil;
import com.atguigu.datingsite.util.SolrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    TUserService tUserService;

    @RequestMapping("/userDetail")
    public String detail(@RequestParam(value = "id")Integer id,
                         @RequestParam(value = "pn",defaultValue = "1")Integer pageNum,
                         @RequestParam(value = "keyword", required = false)String keyword,
                         @RequestParam(value = "ps",defaultValue = "4")Integer pageSize,
                         Model model){
        TUser user = tUserService.getUserByUserId(id);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("trackerServer", "192.168.160.220");
        model.addAttribute("user", user);
        if (! (keyword == null || "".equals(keyword))){
            model.addAttribute("keyword", keyword);
        }
        return "detail";
    }


    @RequestMapping("/search")
    public String search(@RequestParam(value = "pn",defaultValue = "1")Integer pageNum,
                         @RequestParam(value = "keyword", required = false)String keyword,
                         @RequestParam(value = "ps",defaultValue = "4")Integer pageSize,
                         Model model, HttpSession session, RedirectAttributes attributes){

        if (session.getAttribute("loginUser") == null){
            attributes.addFlashAttribute("msg", "请在此登录后再享用搜索功能");
            return "redirect:/login.html";
        }
        try {
            keyword = URLDecoder.decode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Page<Object> page = new Page<>(pageNum, pageSize);

        List<Object> resultList = SolrUtil.queryUserDocumentPage(keyword, pageNum, pageSize);
        if (resultList == null){
            return "search_not_found";
        }
        Object total = resultList.get(0);
        page.setTotal((Long) total);
        List<Map<String, Object>> documentList = (List<Map<String, Object>>) resultList.get(1);

        PageInfo<Object> pageInfo = new PageInfo<>(page, 5);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("documentList", documentList);
        model.addAttribute("trackerServer", "192.168.160.220");
        model.addAttribute("keyword", keyword);
        return "search";
    }


    @RequestMapping("/userLogin")
    public String login(TUser tUser, HttpSession session, RedirectAttributes attributes){
        Enumeration names = session.getAttributeNames();
        while (names.hasMoreElements()){
            String name = (String) names.nextElement();
            session.removeAttribute(name);
        }
        TUser loginUser = tUserService.getUserByUserNameAndPwd(tUser);
        if (loginUser == null){
            attributes.addFlashAttribute("msg","登录失败，用户名或密码错误！");
            attributes.addFlashAttribute("user",tUser);
            return "redirect:/login.html";
        }
        session.setAttribute("loginUser", loginUser);
        session.setAttribute("loginUserTrackerServer", "192.168.160.220");
        return "redirect:/list?pn=1";
    }

    @RequestMapping("/userLogout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/list?pn=1";
    }


    @RequestMapping("/userRegist")
    public String regist(/*@Valid*/ TUser tUser,/* BindingResult bindingResult,*/
                         MultipartFile file, RedirectAttributes attributes){
        String[] resultArray = null;
        if (!file.isEmpty()){
            try {
                resultArray = FastDFSUtil.doUpload(file.getOriginalFilename(), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (resultArray != null){
            tUser.setPictureGroupName(resultArray[0]);
            tUser.setPictureRemoteName(resultArray[1]);
        }

        /*if (bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            Iterator<FieldError> iterator = errors.iterator();
            HashMap<String, Object> errorMap = new HashMap<>();
            while (iterator.hasNext()){
                FieldError next = iterator.next();
                String field = next.getField();
                String message = next.getDefaultMessage();
                errorMap.put(field, message);
            }
            System.out.println(errorMap);
            attributes.addFlashAttribute("msg", "注册失败");
            return "redirect:/reg.html";
        }*/

        List<TUser> userExist = tUserService.getUserByUserName(tUser.getUserName());
        if (userExist.size() > 0){
            attributes.addFlashAttribute("msg", "注册失败，账户名已存在");
            attributes.addFlashAttribute("user", tUser);
            return "redirect:/reg.html";

        }

        boolean result = tUserService.saveUser(tUser);
        if (result){
            attributes.addFlashAttribute("msg","注册成功，请登录！");
            return "redirect:/login.html";
        }else {
            attributes.addFlashAttribute("msg","操作失败，网络故障！");
            attributes.addFlashAttribute("user", tUser);
            return "redirect:/reg.html";
        }
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam(value = "pn",defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "keyword",required = false)String keyword,
                           HttpSession session, Model model){
        if (session.getAttribute("loginUser") == null){
            return "redirect:/login.html";
        }
        if (! (keyword == null || "".equals(keyword))){
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("pageNum", pageNum);
        return "update";
    }

    @RequestMapping("/userUpdate")
    public String update(TUser tUser, MultipartFile file, HttpSession session,
                         RedirectAttributes attributes,Model model,
                         @RequestParam(value = "pn", defaultValue = "1")Integer pageNum,
                         @RequestParam(value = "keyword", required = false)String keyword){

        String[] resultArray = null;
        if (!file.isEmpty()){
            try {
                if (! ("".equals(tUser.getPictureGroupName()) || "".equals(tUser.getPictureRemoteName()))){
                    FastDFSUtil.deleteFile(tUser.getPictureGroupName(), tUser.getPictureRemoteName());
                }
                resultArray = FastDFSUtil.doUpload(file.getOriginalFilename(), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (resultArray != null){
            tUser.setPictureGroupName(resultArray[0]);
            tUser.setPictureRemoteName(resultArray[1]);
        }
        boolean result = tUserService.updateUser(tUser);
        if (result){
            session.removeAttribute("loginUser");
            session.setAttribute("loginUser", tUserService.getUserByUserId(tUser.getUserId()));
            if (! (keyword == null || "".equals(keyword))){
                try {
                    keyword = URLEncoder.encode(keyword,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "redirect:/search?pn=" + pageNum + "&keyword=" + keyword;
            }
            return "redirect:/list?pn=" + pageNum;
        }else {
            attributes.addFlashAttribute("msg", "操作失败，网络故障！");
            attributes.addFlashAttribute("user", tUser);
            return "redirect:/toUpdate?pn=1";
        }
    }

    @RequestMapping("/list")
    public String list(@RequestParam(name = "pn",defaultValue = "1")Integer pageNum,
                       @RequestParam(name = "ps",defaultValue = "4")Integer pageSize,
                       Model model){
        PageHelper.startPage(pageNum, pageSize);
        List<TUser> tUsers = tUserService.listAllUsers();
        PageInfo<TUser> pageInfo = new PageInfo<>(tUsers, 5);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("trackerServer", "192.168.160.220");
        return "list";
    }

}
