package com.supervise.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于MVC返回的通用model object
 */
@JsonInclude(Include.NON_NULL)
public class Result {
	
	/**
	 * 操作结果数据 假设一个操作要返回很多的数据 一个用户名 一个产品 一个相关产品列表 一个产品的评论信息列表 我们以key
	 * value形式进行保存，页面获取data对象读取其对于的value即可
	 */
	private Map<String,Object> data = new HashMap<String,Object>();

	/**
	 * 带状态的操作 比如登录有成功和失败
	 */
	private OperationState operationState = OperationState.DEFAULT;

	/**
	 * 用于在jsp中显示标题的字段 title
	 */
	private String title;

	public Result() {
		super();
	}

	public Result(OperationState operationState, Map<String,Object> data, String title) {
		super();
		this.operationState = operationState;
		this.data = data;
		this.title = title;
	}
	

	/**
	 * 创建一个异常结果
	 * 
	 * @return 一个异常结果实例,不携带异常信息
	 */
	public static Result exception() {
		return Result.me().setOperationState(OperationState.EXCEPTION);
	}

	/**
	 * 未登录
	 * 
	 * @return
	 */
	public static Result unlogin() {
		return Result.me().setOperationState(OperationState.UNLOGINED);
	}

	/**
	 * 创建一个异常结果
	 * 
	 * @param e
	 *            异常
	 * @return 一个异常结果实例,包含参数异常的信息
	 */
	public static Result exception(Exception e) {
		return Result.exception(e.getMessage());
	}

	/**
	 * 创建一个异常结果
	 * 
	 * @param msg
	 *            异常信息
	 * @return 一个异常结果实例,不携带异常信息
	 */
	public static Result exception(String msg) {
		return Result.exception().addData("reason", msg);
	}

	/**
	 * 创建一个带失败信息的result
	 * 
	 * @param reason
	 *            失败原因
	 * @return result实例
	 */
	public static Result fail(String reason) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("reason", reason);
		return Result.me().setOperationState(OperationState.FAIL).setData(data);
	}

	/**
	 * 获取一个result实例
	 * 
	 * @return 一个不携带任何信息的result实例
	 */
	public static Result me() {
		return new Result();
	}

	/**
	 * 创建一个成功结果
	 * 
	 * @return result实例状态为成功无数据携带
	 */
	public static Result success() {
		return Result.me().setOperationState(OperationState.SUCCESS);
	}

	/**
	 * 创建一个成功结果
	 * 
	 * @param data
	 *            需要携带的数据
	 * @return result实例状态为成功数据位传入参数
	 */
	public static Result success(Map<String,Object> data) {
		return Result.success().setData(data);
	}

	/**
	 * 添加更多的数据
	 * 
	 * @param data
	 *            待添加的数据
	 * @return 结果实例
	 */
	public Result addData(Map<String,Object> data) {
		this.data.putAll(data);
		return this;
	}

	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public Result addData(String key, Object object) {
		this.data.put(key, object);
		return this;
	}

	/**
	 * 清空结果
	 */
	public Result clear() {
		this.operationState = OperationState.DEFAULT;
		if (data != null) {
			this.data.clear();
		}
		this.title = "";
		return this;
	}

	public Map<String,Object> getData() {
		return this.data;
	}


	public OperationState getOperationState() {
		return operationState;
	}

	/**
	 * 获取错误获取异常原因
	 * 
	 * @return
	 */
	public String getReason() {
		return (String) getData().get("reason");
	}

	public String getTitle() {
		return title;
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return getOperationState() == OperationState.SUCCESS;
	}

	public Result setData(Map<String,Object> data) {
		this.data = data;
		return this;
	}

	public Result setOperationState(OperationState operationState) {
		this.operationState = operationState;
		return this;
	}

	public Result setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		 ObjectMapper mapper = new ObjectMapper();
		 try {
			 mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
			 mapper.setSerializationInclusion(Include.NON_NULL);  
			return mapper.writeValueAsString(this.data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return this.data.toString();
	}
}
