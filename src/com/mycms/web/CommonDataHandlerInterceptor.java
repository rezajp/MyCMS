package com.mycms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.mycms.domain.Menu;
import com.mycms.repository.MenuRepository;

public class CommonDataHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object obj,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView == null)
			return;
		View view = modelAndView.getView();
		boolean isRedirectView = view != null && view instanceof RedirectView;
		boolean isViewObject = modelAndView.getView() == null;
		// if the view name is null then set a default value of true
		boolean viewNameStartsWithRedirect = (modelAndView.getViewName() == null ? true
				: modelAndView.getViewName().startsWith(
						UrlBasedViewResolver.REDIRECT_URL_PREFIX));

		if (modelAndView.hasView()
				&& ((isViewObject && !isRedirectView) || (!isViewObject && !viewNameStartsWithRedirect))) {
			List<Menu> allMenus = getMenus();
			modelAndView.getModel().put("sitemenus", allMenus);

		}
	}

	private List<Menu> getMenus() {
		ArrayList<String> sortOrders = new ArrayList<String>();
		sortOrders.add("sortOrder");
		Sort sort = new Sort(Direction.ASC, sortOrders);
		List<Menu> allMenus = menuRepository.findAll(sort);
		return allMenus;
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

}
