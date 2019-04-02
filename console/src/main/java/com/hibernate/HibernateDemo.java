package com.hibernate;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateDemo {
    public static void main(String[] args) throws ParseException {
        // testSave();
        // testGet();
        // testDelete(1);
        // List<User> users = getUserList();
        // System.out.println(users.size());
        // testUpdate();
        // queryUsers();
        // queryStudent();

        /*
         * from之后必须跟Class,这里不是数据库表，而是对model进行查询查找，最终转成对应的sql 1.排序查询
         */
        // testQuery("from User order by id desc");

        // 查询指定的多列，返回的结果集不是不是对象集合，而是每一行的Object[]对象集合
        // testQueryColumns("select u.name,u.gender from User as u order by u.id desc");

        // 指定列查询封装成对象，需要调用类的构造函数
        // testQueryColumns1("select new User(u.id,u.name) from User as u order by u.id
        // desc");

        // 条件查询，命名参数查询
        // testParameterQuery();

        // testOneToMany();

        // testSessionCache();
        // testSessionCache2();
        // testSessionData();

        testOrignalSql();
        //testStoreProcedure();
        /*
         * 通过code建库，然后建表，加入初始数据 但是实际项目中一般都是提前设计好数据库表结构，然后在一个xml中创建映射关系。
         */
        // createSchema();
    }

    static void testStoreProcedure() {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            Date result = (Date) session.createSQLQuery("call usp_get_server_date()").uniqueResult();
            tx.commit();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    static void testOrignalSql() {
        Session session = null;
        try {
            Configuration config = new Configuration();
            config.configure();
            SessionFactory sf = config.buildSessionFactory();
            session = sf.openSession();
            // Transaction tx = session.beginTransaction();
            List<User> uList = session.createSQLQuery("select * from user").addEntity(User.class).list();
            System.out.println(uList.size());
            // tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    static void testOneToMany() {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            Teacher teacher = (Teacher) session.get(Teacher.class, 1);
            Set<Student> stus = teacher.getStudents();// 一对多的关系，
            System.out.println(teacher.getName() + ", " + teacher.getType());
            for (Student stu : stus) {
                System.out.println(stu.getName());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    static void testOneToOne() {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            Student student = (Student) session.get(Student.class, 1);
            Teacher teacher = (Teacher) session.get(Teacher.class, student.getTeacherID());
            System.out.println(student.getName() + ", teacher is " + teacher.getName() + " " + teacher.getType());
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    static void testParameterQuery() {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from User where gender=:gender").setString("gender", "M");
            List<User> list = query.list();
            for (User u : list) {
                System.out.print(u.getId() + "," + u.getName() + "," + u.getGender());
                System.out.println();
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    static void testQueryColumns1(String sql) {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(sql);
            List<User> list = query.list();
            for (User u : list) {
                System.out.print(u.getId() + "," + u.getName());
                System.out.println();
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    static void testQueryColumns(String sql) {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(sql);
            List<Object> list = query.list();
            for (Object o : list) {
                Object[] oArr = (Object[]) o;
                System.out.print(oArr[0] + "," + oArr[1]);
                System.out.println();
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /*
     * 不同的session共享数据问题
     */
    static void testSessionData() {
        Session session1 = null;
        Session session2 = null;
        try {
            session1 = HibernateUtils.getSession();
            session2 = HibernateUtils.getSession();
            Transaction tx1 = session1.beginTransaction();
            Transaction tx2 = session2.beginTransaction();
            User u = (User) session1.get(User.class, 2);// user对象被session1关联
            session2.update(u);// user对象和session2建立了关联
            u.setName("TEST DATA");
            tx1.commit();// 执行session1的update语句
            tx2.commit();// 执行session2的update语句
            // 会发送两条update语句，更新的是同一个user数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session1.close();
            session2.close();
        }
    }

    /*
     * list()查询出来的结果会被缓存起来，当Iterator再次查询的时候，会先发送查询id的sql，
     * 但是查询实体的SQL不会发出，它会首先去一级缓存中获取一级缓存的数据。 list()只会发送一个查询语句，把符合条件的数据全部查询出来，效率高
     * iterate()会先发送一个查询id的语句，然后执行next()的时候，会再次发送一个以id为条件的查询语句。多次发送sql语句，效率低。
     */
    static void testSessionCache2() {
        Session session = null;
        List<User> users = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("from User");
            users = query.list();
            // session.clear(); 不清除缓存，Iterator执行的时候不会发送查询语句，

            Iterator<User> it = query.iterate();
            while (it.hasNext()) {
                System.out.println(it.next());
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /*
     * get()先将获取的对象存储到一级缓存中，当再次加载同一个持久化对象的时候，先检测一级缓存中是否有该对象， 如果有直接获取，不再发送sql语句
     */
    static void testSessionCache() {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            User u = (User) session.get(User.class, 2);
            /*
             * evict(), clear()会清除缓存
             */
            // session.evict(u); //session.clear();
            session.get(User.class, 2);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    static void testQuery(String sql) {
        Session session = null;
        List<User> users = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            // User是映射的User class,对应User.hbm.xml中的class name。
            users = session.createQuery(sql).list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (users != null) {
            for (User u : users) {
                System.out.println(u.getId() + "," + u.getName());
            }
        }
        // return users;

    }

    static void createSchema() {
        // 加载默认得hibernate.cfg.xml配置文件
        Configuration config = new Configuration().configure();
        // 加载hbm文件（User.hbm.xml）
        config.addClass(Student.class);
        // 根据配置生成表
        SchemaExport schema = new SchemaExport(config);
        schema.create(true, true);
        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        Transaction tran = session.beginTransaction();
        Student u = new Student();
        u.setName("Lily");
        u.setAge(34);
        session.save(u);
        tran.commit();
        session.close();
    }

    static void testGet() {
        // 1. 创建配置管理器对象
        Configuration config = new Configuration();
        // 2. 加载主配置文件， 默认加载src/hibernate.cfg.xml
        config.configure();
        // 3. 根据加载的主配置文件，创建对象
        // 构造sessionFactory很消耗资源，一般情况下，一个应用中只初始化一个SessionFactory对象。
        SessionFactory sf = config.buildSessionFactory();
        // 4. 创建Session对象
        Session session = sf.openSession();
        // 5. 开启事务
        Transaction tx = session.beginTransaction();

        // --- 获取 (根据主键查询)
        User users = (User) session.get(User.class, 1);

        System.out.println(users.getName());

        // 6. 提交事务/关闭session
        tx.commit();
        session.close();
    }

    static void testSave() throws ParseException {
        // 对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setName("jack");
        user.setAddon(simpleDateFormat.parse("1989-09-24"));
        user.setGender("F");

        // 1. 创建配置管理器对象
        Configuration config = new Configuration();
        // 2. 加载主配置文件， 默认加载src/hibernate.cfg.xml
        config.configure();
        // 3. 根据加载的主配置文件，创建对象
        SessionFactory sf = config.buildSessionFactory();
        // 4. 创建Session对象
        Session session = sf.openSession();
        // 5. 开启事务
        Transaction tx = session.beginTransaction();

        // --- 保存
        session.save(user);

        // 6. 提交事务/关闭session
        tx.commit();
        session.close();

    }

    static void testDelete(int id) {
        Session session = null;
        try {
            // 创建session
            session = HibernateUtils.getSession();
            // 开启事务
            Transaction tx = session.beginTransaction();
            // -- 先查询， 再删除 ---
            Object obj = session.get(User.class, id);
            // 判断
            if (obj != null) {
                session.delete(obj);
            }

            // 提交事务
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭session
            session.close();
        }

    }

    static User testFindByID(int id) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            // -- 主键查询 --
            user = (User) session.get(User.class, id);
            tx.commit();
        } finally {
            session.close();
        }
        return user;
    }

    static List<User> getUserList() {
        Session session = null;
        List<User> users = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            // User是映射的User class,对应User.hbm.xml中的class name。
            users = session.createQuery("from User").list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    static void testUpdate() {
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            User users = (User) session.get(User.class, 2);
            users.setGender("M");
            // -- 保存 --
            session.update(users);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭session
            session.close();
        }
    }

    static List<User> queryUsers() {
        Session session = null;
        List<User> list = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            // 获取Criteria接口
            Criteria c = session.createCriteria(User.class);
            // 设置查询条件
            c.add(Restrictions.eq("gender", "F"));

            list = c.list();

            System.out.println(list);
            tx.commit();
        } finally {
            session.close();
        }

        return list;
    }

    static List<Student> queryStudent() {
        Session session = null;
        List<Student> list = null;
        try {
            session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            // 获取Criteria接口
            Criteria c = session.createCriteria(Student.class);
            // 设置查询条件
            c.add(Restrictions.eq("name", "lily"));

            list = c.list();

            System.out.println(list);
            tx.commit();
        } finally {
            session.close();
        }

        return list;
    }
}
