package cn.edu.sdut.openeshop.tools;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.UserTransaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import cn.edu.sdut.openeshop.model.Goods;
import cn.edu.sdut.openeshop.model.Member;
import cn.edu.sdut.openeshop.model.Product;


@Startup
@Singleton
public class SeedDataImporter {
    @Inject
    @Category("openeshop")
    private Logger log;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction tx;

    @PostConstruct
    public void importData() {
        Member member1 = new Member();
        member1.setUsername("admin");
        member1.setPassword("admin");
        member1.setEmail("admin@example.com");
        
        Member member2 = new Member();
        member2.setUsername("user");
        member2.setPassword("user");
        member2.setEmail("user@example.com");
        
        Goods goods1 = new Goods();
        goods1.setName("电脑");
        goods1.setCode("100");
        goods1.setStore(100);
        goods1.setDescription("电脑");
        
        Goods goods2 = new Goods();
        goods2.setName("书籍");
        goods2.setCode("200");
        goods2.setStore(100);
        goods2.setDescription("书籍");
        
        Product p1 = new Product();
        p1.setGoods(goods1);
        p1.setName("ThinkPad");
        p1.setCode("101");
        p1.setStore(10);
        p1.setPrice(new BigDecimal(3000));
        p1.setDescription("ThinkPad，中文名为“思考本”， 在2005年以前是IBMPC事业部旗下的便携式计算机品牌，" +
        		"凭借坚固和可靠的特性在业界享有很高声誉。在Lenovo收购IBMPC事业部之后，ThinkPad商标为Lenovo所有。" +
        		"ThinkPad自问世以来一直保持着黑色的经典外观并对技术有着自己独到的见解，如：" +
        		"TrackPoint（指点杆，俗称小红点）、ThinkLight键盘灯、全尺寸键盘和APS(Active Protection System，主动保护系统)。");

        Product p2 = new Product();
        p2.setGoods(goods1);
        p2.setName("PowerEdge R910");
        p2.setCode("102");
        p2.setStore(10);
        p2.setPrice(new BigDecimal(53000));
        p2.setDescription("Dell™ PowerEdge R910™ 是一款高性能4插槽4U机架式服务器，它具备内置可靠性与可扩展性，适用于关键任务应用程序。");

        Product p3 = new Product();
        p3.setGoods(goods2);
        p3.setName("对抗语文");
        p3.setCode("201");
        p3.setStore(10);
        p3.setPrice(new BigDecimal(17));
        p3.setDescription("《对抗语文-让孩子读到世界上最好的文字》，本书分为“语文是什么”、“悦读美好”及“语文之痛”三部分，" +
        		"包括：“我们可以从老的国语课本里借鉴点什么”、“《功夫熊猫》中闪烁的教育真谛”、“宫崎骏动画的迷人光辉”等内容。");
        
        Product p4 = new Product();
        p4.setGoods(goods2);
        p4.setName("企业级Java开发-基于CDI和JBoss 6+");
        p4.setCode("202");
        p4.setStore(10);
        p4.setPrice(new BigDecimal(23));
        p4.setDescription("《企业级Java开发-基于CDI和JBoss 6+》是一本引导读者熟悉CDI和JBoss6+环境下进行Java EE开发的书籍，" +
        		"本书通篇以一个小型电子商务系统openeshop为例，讲述了如何从一个原型逐步发展成为功能完善的电子商务平台，让读者从" +
        		"代码的演变过程中逐步了解Java EE技术是如何在实践中使用的。");
        
        try {
            try {
                em.persist(member1);
                em.persist(member2);
                em.persist(goods1);
                em.persist(goods2);
                em.persist(p1);
                em.persist(p2);
                em.persist(p3);
                em.persist(p4);
            } catch (TransactionRequiredException e) {
                // manual transaction control required in @PostConstruct method
                // only use if enforced by JPA provider (due to bug in GlassFish)
                tx.begin();
                em.persist(member1);
                em.persist(member2);
                em.persist(goods1);
                em.persist(goods2);
                em.persist(p1);
                em.persist(p2);
                em.persist(p3);
                em.persist(p4);
                tx.commit();
            }
            log.info("Successfully imported seed data.");
        } catch (Exception e) {
            log.warn("Seed data import failed.", e);
        }
    }
}
