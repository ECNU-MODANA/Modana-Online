package com.smartcold.zigbee.manage.controller;

import com.smartcold.zigbee.manage.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description: ColdStorageController
 * Author: qiunian.sun
 * Update: qiunian.sun(2016-03-15 23:38)
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    /*    @Autowired
        private CommentMapper commentMapper;

        @RequestMapping(value = "/findCommentsByRDCId", method = RequestMethod.GET)
        @ResponseBody
        public Object findCommentsByRDCId(@RequestParam int rdcID, @RequestParam int npoint) {
            return commentMapper.findLastNComment(rdcID, npoint);
        }*/

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/findCommentsByRDCId", method = RequestMethod.GET)
    @ResponseBody
    public Object findCommentsByRDCId(@RequestParam int rdcID, @RequestParam int npoint) {
        return commentService.findCommentsRdcID(rdcID, npoint);
    }
}
