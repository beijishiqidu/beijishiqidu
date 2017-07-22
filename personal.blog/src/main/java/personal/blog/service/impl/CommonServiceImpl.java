package personal.blog.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personal.blog.dao.GenericDao;
import personal.blog.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger LOGGER = Logger.getLogger(CommonServiceImpl.class);

    @Autowired
    private GenericDao genericDao;
    
}
