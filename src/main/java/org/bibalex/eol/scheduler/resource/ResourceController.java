package org.bibalex.eol.scheduler.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by sara.mustafa on 4/18/17.
 */
@RestController
@RequestMapping("{contentPartnerId}/resources")
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.POST)
    public Callable<ResponseEntity<?>> createResource(@PathVariable long contentPartnerId, @RequestBody Resource resource){
        logger.debug("Create new resource with partner id "+contentPartnerId);
        return () -> ResponseEntity.ok(resourceService.createResource(contentPartnerId, resource));
    }
    @RequestMapping(method = RequestMethod.POST, value = "/{resourceId}")
    public Callable<ResponseEntity<Resource>> updateRsource(@PathVariable long contentPartnerId, @PathVariable long resourceId, @RequestBody Resource resource){
        logger.debug("Update resource with id "+resourceId );
        return () ->  ResponseEntity.ok(resourceService.updateResource(contentPartnerId, resourceId, resource));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Resource>> getResources(@PathVariable long contentPartnerId){
        List<Resource> resources = resourceService.getResources(contentPartnerId);
        HttpStatus status = HttpStatus.OK;
        if (resources == null)
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(resources, status);
    }




}

