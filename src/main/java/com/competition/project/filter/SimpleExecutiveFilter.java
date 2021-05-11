package com.competition.project.filter;


import com.competition.project.utils.IpUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 黑名单可执行程序请求过滤器
 */
// 暂且不用
@Component
public class SimpleExecutiveFilter extends AuthorizationFilter {

    protected static final String[] blackUrlPathPattern = new String[] { "*.aspx*", "*.asp*", "*.php*", "*.exe*",
            "*.jsp*", "*.pl*", "*.py*", "*.groovy*", "*.sh*", "*.rb*", "*.dll*", "*.bat*", "*.bin*", "*.dat*",
            "*.bas*", "*.c*", "*.cmd*", "*.com*", "*.cpp*", "*.jar*", "*.class*", "*.lnk*" };

    private static final Logger log = LoggerFactory.getLogger(SimpleExecutiveFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object obj) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String reqUrl = httpRequest.getRequestURI().toLowerCase().trim();

        for (String pattern : blackUrlPathPattern) {
            if (PatternMatchUtils.simpleMatch(pattern, reqUrl)) {
                log.error(new StringBuffer().append("unsafe request >>> ").append(" request time: ").append(
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("; request ip: ")
                        .append(IpUtils.getIpAddress(httpRequest)).append("; request url: ").append(httpRequest.getRequestURI())
                        .toString());
                return false;
            }
        }

        return true;

    }

}