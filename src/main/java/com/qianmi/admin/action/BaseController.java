package com.qianmi.admin.action;

import com.qianmi.admin.common.constants.ErrorCode;
import com.qianmi.admin.common.exception.AdminRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller基类，放置用到的公共资源
 * 比如数据格式转换公共方法、操作日志记录的方法
 */
@Scope("request")
public class BaseController {

    protected final transient Logger logger = LoggerFactory.getLogger("inf");

    /**
     * The action execution was successful.
     */
    public static final String SUCCESS = "success";

    /**
     * The action execution was a fail.
     */
    public static final String FAIL = "fail";

    /**
     * The Remote execution was a error
     */
    public static final String ERROR = "error";

    /**
     * 成功的Status Code
     */
    private static final int RESCODE_OK = 200;
    /**
     * 失败的Status Code
     */
    private static final int RESCODE_FAIL = 201;

    /**
     * 通过config.properties文件中的zimg.server注入值
     */
//    @Value("${zimg.server}")
//    private String zimgServer;

    /**
     * Jquery DataTable Data
     *
     * @param totalCount
     * @param dataList
     * @return
     */
    protected Map<String, Object> dataTableJson(int totalCount, List<?> dataList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", dataList == null ? Collections.EMPTY_LIST : dataList);
        map.put("total", totalCount);
        return map;
    }

    /**
     * Jquery DataTable Data
     *
     * @param totalCount
     * @param count
     * @param dataList
     * @return
     */
    protected Map<String, Object> dataTableJson(int totalCount, int count, List<?> dataList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("iTotalDisplayRecords", totalCount);
        data.put("iTotalRecords", totalCount);
        data.put("recycleRecords", count);
        data.put("aaData", dataList == null ? Collections.EMPTY_LIST : dataList);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("result", SUCCESS);
        map.put("data", data);
        return map;
    }

    protected Map<String, Object> dataTableJson(List<?> dataList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("aaData", dataList == null ? Collections.EMPTY_LIST : dataList);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("result", SUCCESS);
        map.put("data", data);
        return map;
    }

    protected Map<String, Object> data2json(List<?> data) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (CollectionUtils.isEmpty(data)) {
            map.put("result", ERROR);
        } else {
            map.put("result", SUCCESS);
        }
        map.put("data", data);
        return map;
    }

    protected Map<String, Object> data2json(Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (data == null) {
            map.put("result", ERROR);
        } else {
            map.put("result", SUCCESS);
        }
        map.put("data", data);
        return map;
    }

    /**
     * 获取默认ajax成功信息
     */
    protected Map<String, Object> getSuccessResult() {
        return getResult(true, RESCODE_OK, "操作成功！", Collections.EMPTY_MAP);
    }

    /**
     * 描述：获取成功结果
     *
     * @param obj
     * @return
     */
    protected Map<String, Object> getSuccessResult(Object obj) {
        return getResult(true, RESCODE_OK, "操作成功", obj);
    }

    /**
     * 描述：获取失败结果
     *
     * @param msg
     * @return
     */
    protected Map<String, Object> getFailResult(String msg) {
        return getResult(false, RESCODE_FAIL, msg, Collections.EMPTY_MAP);
    }

    /**
     * 描述：获取返回结果
     *
     * @param isOk
     * @param resCode
     * @param errorMsg
     * @param obj
     * @return
     */
    protected Map<String, Object> getResult(boolean isOk, int resCode, String errorMsg, Object obj) {
        return createJson(isOk, resCode, errorMsg, obj);
    }

    /**
     * 描述：组装json格式返回结果
     *
     * @param isOk
     * @param resCode
     * @param errorMsg
     * @param obj
     * @return
     */
    protected Map<String, Object> createJson(boolean isOk, int resCode, String errorMsg, Object obj) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("success", isOk);
        jsonMap.put("code", resCode);
        jsonMap.put("message", errorMsg);
        jsonMap.put("data", obj);
        return jsonMap;
    }

    /**
     * 获取request
     *
     * @return
     */
    protected HttpServletRequest getRequestContext() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    /**
     * 获取session
     *
     * @return
     */
    protected HttpSession getSessionContext() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession(false);
    }

    /**
     * 获取登陆用户工号
     *
     * @return
     */
    protected String getEmployeeId() {
        return (String) getSessionContext().getAttribute("empNo");
    }

    /**
     * 根据相对路径获取资源绝对路径
     *
     * @param path
     * @return
     */
    protected String getRealPath(String path) {
        ServletContext app = getSessionContext().getServletContext();
        if (null != app) {
            String root = app.getRealPath(String.valueOf(File.separatorChar));
            return root + path;
        }
        return path;
    }

    /**
     * 判断请求是不是ajax请求
     * hufeng(of730)
     *
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }


//    protected String getThumbUrl(String fileUrl) {
//        //拼接图片全地址（缩略图）
//        List<String> fullImageSplitter = new ArrayList<String>(Arrays.asList(fileUrl.split("\\.")));
//        fullImageSplitter.add(1, "-50-50.");
//        String thumbImgUrl = StringUtils.join(fullImageSplitter, "");
//
//        return String.format("%s/%s", zimgServer, thumbImgUrl);
//    }

    protected void checkParamResult(BindingResult result) throws AdminRuntimeException {
        if (result.hasErrors()) {
            String msg = result.getFieldError().getDefaultMessage();
            throw new AdminRuntimeException(ErrorCode.COMMON.PARAMETER_EXCEPTION.getErrorCode());
        }
    }

}