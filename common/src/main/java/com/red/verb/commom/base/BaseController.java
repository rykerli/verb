package com.red.verb.commom.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.red.verb.commom.ServerResponse;
import com.red.verb.commom.entity.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * base controller
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/24     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-24 16:02
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
@Slf4j
public class BaseController<Service extends IService, T> {
	private Service service;

	@Autowired
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * 获取所有数据
	 *
	 * @return 返回前端数据
	 */
	@GetMapping("getList")
	public ServerResponse getList() {
		List<T> list = service.list();
		return ServerResponse.createBySuccess(list);
	}

	/**
	 * @param page 分页实体
	 * @return 返回前端数据
	 */
	@GetMapping("getPageList")
	public ServerResponse getPageList(@RequestBody BasePage page) {
		Page<T> pojo = new Page<>(page.getCurrent(), page.getSize());
		IPage<T> iPage = service.page(pojo);
		log.info("总条数 ------>{}", iPage.getTotal());
		log.info("当前页数 ------>{}", iPage.getCurrent());
		log.info("当前每页显示数 ------>{}", iPage.getSize());
		log.info("数据 ------>{}", iPage.getRecords());
		return ServerResponse.createBySuccess(iPage);
	}

	/**
	 * @param pojo 实体
	 * @return 返回前端数据
	 */
	@PostMapping("addData")
	public ServerResponse addData(@RequestBody T pojo) {
		boolean flag = service.save(pojo);
		if (flag) {
			return ServerResponse.createBySuccess("成功添加一条数据");
		}
		return ServerResponse.createByError();
	}

	/**
	 * 添加一组数据
	 *
	 * @param pojo 实体类的集合
	 * @return 返回前端json数据
	 */
	@PostMapping("addDataBatch")
	public ServerResponse addData(@RequestBody Collection<T> pojo) {
		boolean flag = service.saveBatch(pojo, 500);
		return ServerResponse.createByError();
	}

	/**
	 * 根据id修改一条数，数据中必须有主键id字段
	 *
	 * @param pojo 任意实体类
	 * @return 返回前端json数据
	 */
	@PutMapping("updateById")
	public ServerResponse updateById(@RequestBody T pojo) {
		boolean flag = service.updateById(pojo);
		if (flag) {
			return ServerResponse.createBySuccess("成功修改一条数据");
		}
		return ServerResponse.createByError();
	}

	/**
	 * 根据其他属性字段修改一条数据
	 *
	 * @param pojo   任意类型实体类
	 * @param column 具体字段
	 * @param value  具体字段的值
	 * @return 返回前端json数据
	 */
	@PostMapping("updateByColumn")
	public ServerResponse update(@RequestBody T pojo, @RequestParam("column") String column,
								 @RequestParam(name = "value") String value) {
		Wrapper<T> wrapper = Wrappers.<T>update().eq(column, value);
		boolean flag = service.update(pojo, wrapper);
		if (flag) {
			return ServerResponse.createBySuccess("成功修改一条数据");
		}
		return ServerResponse.createByError();
	}

	/**
	 * 要根据哪个字段删除一条数据
	 *
	 * @param column 要根据哪个字段删除
	 * @param value  字段的值
	 * @return 返回前端json数据
	 */
	@DeleteMapping("deleteByColumn")
	public ServerResponse delete(@RequestParam("column") String column, @RequestParam("value") String value) {
		Wrapper<T> ew = Wrappers.<T>query().eq(column, value);
		boolean flag = service.remove(ew);
		if (flag) {
			return ServerResponse.createBySuccess("成功删除一条数据");
		}
		return ServerResponse.createByError();
	}

	/**
	 * 根据id删除一条数据
	 *
	 * @param id 传入的id值
	 * @return 返回前端json数据
	 */
	@DeleteMapping("deleteById")
	public ServerResponse delete(@RequestParam("id") Integer id) {
		boolean flag = service.removeById(id);
		if (flag) {
			return ServerResponse.createBySuccess("成功删除一条数据");
		}
		return ServerResponse.createByError();
	}

	/**
	 * 根据一组id删除数据
	 *
	 * @param ids 一组id
	 * @return 返回前端json数据
	 */
	@DeleteMapping("deleteByIds")
	public ServerResponse delete(@RequestParam(value = "ids[]") List<Integer> ids) {
		boolean flag = service.removeByIds(ids);
		if (flag) {
			return ServerResponse.createBySuccess("成功删除一组数据");
		}
		return ServerResponse.createByError();
	}
}
