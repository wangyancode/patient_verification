//package com.dincher.framework.interceptor;
//
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Signature;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import java.sql.Connection;
//import java.util.Properties;
//
///**
// * MyBatis 允许使用插件来拦截的方法调用包括：
// *     Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
// *     ParameterHandler (getParameterObject, setParameters)
// *     ResultSetHandler (handleResultSets, handleOutputParameters)
// *     StatementHandler (prepare, parameterize, batch, update, query)
// *
// *  拦截器顺序：
// *       Executor -> ParameterHandler -> StatementHandler -> ResultSetHandler
// *
// */
////@Intercepts({
////        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}),
////        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class,ResultHandler.class})
////})
//@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
//@Component
//public class SqlInterceptor implements Interceptor, HandlerInterceptor {
//
//    public Object intercept(Invocation invocation) throws Throwable {
//        System.out.println("------------------------sql拦截start-----------");
//        Object target = invocation.getTarget();
//        if (target instanceof StatementHandler) {
//            StatementHandler handler = (StatementHandler) target;
//            String sql = handler.getBoundSql().getSql();
////            Object param = handler.getParameterHandler().getParameterObject();
////            System.out.println("sql : " + sql.replace("?", param + ""));
//            System.out.println("sql : " + sql);
//        } else if (target instanceof Executor) {
//            Object[] args = invocation.getArgs();
//            MappedStatement mappedStatement = (MappedStatement) args[0];
//            String methodName = mappedStatement.getId();
//            System.out.println("method : " + methodName);
//            BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
//            String sql = boundSql.getSql();
////            Object param = boundSql.getParameterObject();
////            System.out.println("sql : " + sql.replace("?", param + ""));
//            System.out.println("sql : " + sql);
//        }
//        Object result = invocation.proceed();
//        System.out.println("------------------------sql拦截end-----------");
//        return result;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Interceptor.super.plugin(target);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        Interceptor.super.setProperties(properties);
//    }
//}