package com.cbr.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cbr.university.model.Group;
import com.cbr.university.model.Room;
import com.cbr.university.model.ScheduleLine;
import com.cbr.university.model.Teacher;
import com.cbr.university.service.BaseService;

@Controller
@RequestMapping("schedule-lines")
public class ScheduleLineController {
    private ModelAndView mv = new ModelAndView();
    private BaseService<ScheduleLine> scheduleLineService;
    private BaseService<Group> groupService;
    private BaseService<Room> roomService;
    private BaseService<Teacher> teacherService;

    @Autowired
    public ScheduleLineController(BaseService<ScheduleLine> scheduleLineService,
            BaseService<Group> groupService, BaseService<Room> roomService,
            BaseService<Teacher> teacherService) {
        this.scheduleLineService = scheduleLineService;
        this.groupService = groupService;
        this.roomService = roomService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public ModelAndView getAll() {
        mv.clear();
        mv.setViewName("schedule-lines");
        mv.addObject("scheduleLines", scheduleLineService.getAll());
        return mv;
    }

    @GetMapping("add")
    public ModelAndView add() {
        mv.clear();
        mv.setViewName("schedule-line-add");
        mv.addObject("scheduleLine", new ScheduleLine());
        mv.addObject("groups", groupService.getAll());
        mv.addObject("teachers", teacherService.getAll());
        mv.addObject("rooms", roomService.getAll());
        return mv;
    }

    @PostMapping("add")
    public ModelAndView add(ScheduleLine scheduleLine, BindingResult result) {
        scheduleLineService.create(scheduleLine);
        return getAll();
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@PathVariable int id) {
        mv.clear();
        mv.setViewName("schedule-line-edit");
        mv.addObject("scheduleLine", scheduleLineService.getById(id));
        mv.addObject("groups", groupService.getAll());
        mv.addObject("teachers", teacherService.getAll());
        mv.addObject("rooms", roomService.getAll());
        return mv;
    }

    @PostMapping("edit/{id}")
    public ModelAndView edit(ScheduleLine scheduleLine, BindingResult result) {
        scheduleLineService.update(scheduleLine);
        return getAll();
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable int id) {
        scheduleLineService.delete(scheduleLineService.getById(id));
        return getAll();
    }
}
