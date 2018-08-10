package com.myFileSys.blog;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.myFileSys.common.model.Blog;
import com.myFileSys.common.model.Memu;
import com.myFileSys.common.model.User;
import com.myFileSys.files.MemuService;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	
	BlogService service = BlogService.me;
	
	public void index() {
		User user = (User) getRequest().getSession().getAttribute("user");
//		List<Memu> memu =  (List<Memu>) getRequest().getSession().getAttribute("memu");
		List<Memu> memu = MemuService.me.getMemus(user.getId());
//		List<Memu> memu = getAttr("memu");
		setAttr("user", user);
		setAttr("memu", memu);
		setAttr("blogPage", service.paginate(getParaToInt(0, 1), 10));
		render("blog.html");
	}
	
	public void add() {
	}
	
	/**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	@Before(BlogValidator.class)
	public void save() {
		getBean(Blog.class).save();
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", service.findById(getParaToInt()));
	}
	
	/**
	 * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
	 * 并要对数据进正确性进行验证，在此仅为了偷懒
	 */
	@Before(BlogValidator.class)
	public void update() {
		getBean(Blog.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		service.deleteById(getParaToInt());
		redirect("/blog");
	}
}


