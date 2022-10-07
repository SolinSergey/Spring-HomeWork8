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
        try {
            Session session = daoConnection.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            product = session.get(Product.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> findAll() {
        List<Product> list = null;
        try {
            Session session = daoConnection.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from Product").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteById(Long id) {
        try {
            Session session = daoConnection.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
