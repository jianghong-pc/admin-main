package com.qianmi.admin.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 搜索表单公共属性
 */
public class Pagination {
	/**
	 * 开始索引
	 */
	private int page;

	/**
	 * 要显示的数量
	 */
	private int pageSize;

	/**
	 * 所有可以排序的列
	 */
	private List<String> sColumns;

	/**
	 * 要排序列数
	 */
	private Integer iSortingCols;

	/**
	 * 排序信息,使用set方法注入
	 */
	private String sortInfo;

	public Pagination() {
	}

	public Pagination(HttpServletRequest request, int page,
			int pageSize) {
		this.setSortInfo(request);
		this.page = page;
		this.pageSize = pageSize;
	}

	/**
	 * 排序信息，排序列格式转换，Controller中设置值：pagination.setSortInfo(request);
	 * 
	 * <pre>
	 * 例:
	 *    sSortDir_0  = 1
	 *    sSortDir_1  = 2
	 *    iSortCol_0  = desc
	 *    iSortCol_1  = asc
	 *    columns     = [id,userName,no,time,]
	 * 格式化返回:
	 *    result      = "userName_desc,no_asc" OR "userName desc,no asc"
	 * 依赖属性:
	 *    sSortDir_0..n 要排序列编号，参考字段: {@link #sColumns}
	 *    iSortCol_0..n 要排序列排序方式: desc OR asc
	 * iSortingCols  要排序列数量
	 * 
	 * <pre>
	 * 
	 * @param request HttpServletRequest
	 */
	public void setSortInfo(HttpServletRequest request) {
		if (iSortingCols == null || iSortingCols == 0) {
			setSortInfo("");
			return;
		}
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < iSortingCols; i++) {
			int index = Integer.parseInt(request.getParameter("iSortCol_" + i));
			String sortType = request.getParameter("sSortDir_" + i);
			result.append(sColumns.get(index));
			result.append(" ");
			result.append(sortType);
			result.append(",");
		}
		// 删除结尾逗号
		setSortInfo(StringUtils.removeEnd(result.toString(), ","));
	}

	/**
	 * 默认不使用
	 * 
	 * @param sortInfo
	 */
	public void setSortInfo(String sortInfo) {
		this.sortInfo = sortInfo;
	}

	/**
	 * 获取排序信息 {@link #setSortInfo(HttpServletRequest)}
	 * 
	 * @return 列名 + 空格 + 排序方式 + 逗号, 例: "userName_desc,no_asc" OR
	 *         "userName desc,no asc"
	 */
	public String getSortInfo() {
		return sortInfo;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setsColumns(List<String> sColumns) {
		this.sColumns = sColumns;
	}

	public Integer getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(Integer iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	@Override
	public final String toString() {
		return JSON.toJSONString(this);
	}

}
