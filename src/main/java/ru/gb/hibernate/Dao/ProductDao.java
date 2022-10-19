package ru.gb.hibernate.Dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gb.hibernate.entities.Product;
import javax.persistence.Query;
import java.util.List;

@Component
public class ProductDao {
    DaoConnection daoConnection = null;

    @Autowired
    public void setDaoConnection(DaoConnection daoConnection) {
        this.daoConnection = daoConnection;
    }

    public Product findById(Long id) {
        Product product = null;
        try (Session session = daoConnection.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            product = session.get(Product.class, id);
            session.getTransaction().commit();
        }
        return product;
    }
    public int getAmountPages (){
        int fullAmountRecord=0;
        try (Session session = daoConnection.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Product> list = session.createQuery("from Product").getResultList();
            fullAmountRecord=list.size();
            session.getTransaction().commit();
            if (fullAmountRecord % 10!=0){
                fullAmountRecord=fullAmountRecord/10+1;
            }
            else{
                fullAmountRecord=fullAmountRecord/10;
            }
        }
        return fullAmountRecord;
    }
    public List<Product> findAll(int page) {
        List<Product> pr;
        try (Session session = daoConnection.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            pr = session.createQuery("SELECT p  FROM Product p order by p.id asc", Product.class)
                    .setFirstResult((page-1)*10)
                    .setMaxResults(10)
                    .getResultList();
            session.getTransaction().commit();
        }
        return pr;
    }

    public void deleteById(Long id) {
        try (Session session = daoConnection.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    public Product saveOrUpdate(Product product) {
        Product productNew = new Product();
        try {
            Session session = daoConnection.getSessionFactory().getCurrentSession();
            productNew.setTitle(product.getTitle());
            productNew.setPrice(product.getPrice());

            session.beginTransaction();
            session.saveOrUpdate(productNew);
            session.getTransaction().commit();

            session = daoConnection.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            String hql = "FROM Product P WHERE P.title = :title";
            Query query = session.createQuery(hql);
            query.setParameter("title", productNew.getTitle());
            productNew = (Product) query.getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productNew;
    }
}
