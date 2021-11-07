package org.school.diary.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.school.diary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class UserPrincipalDetailService extends HibernateDaoSupport implements UserDetailsService{


    @Autowired
    public UserPrincipalDetailService(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*User user = userRepository.findByEmail(username);

      //  System.out.println("Znaleziony uzytkownik: "+ userPrincipal.getUsername() + " haslo: "+ userPrincipal.getPassword());
        return userPrincipal;*/
        User user = (User) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery("select u from User u where email = ?");
                query.setParameter(0, username);
                return query.uniqueResult();
            }
        });
        if (user == null) {
            throw new UsernameNotFoundException("Unauthorized");
        }
        return new UserPrincipal(user);
    }
}
