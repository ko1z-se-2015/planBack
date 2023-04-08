package com.example.volunteer.services;

import com.example.volunteer.entities.Report;
import com.example.volunteer.entities.User;
import com.example.volunteer.repositories.ReportRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportService {

    private final ReportRepo reportRepo;

    public void createReport(Report report) {
        reportRepo.save(report);
    }

    public List<Report> getReportsBySender(User user) {
        return reportRepo.findAllBySendBy(user);
    }

    public List<Report> getReportsBySendTo(User user) {
        return reportRepo.findAllBySendTo(user);
    }
}
