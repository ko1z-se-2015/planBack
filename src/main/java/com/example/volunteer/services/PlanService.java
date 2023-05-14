package com.example.volunteer.services;

import com.example.volunteer.entities.*;
import com.example.volunteer.modules.*;
import com.example.volunteer.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PlanService {

    private final PlanRepo planRepo;
    private final AcademicWorkRepo academicWorkRepo;
    private final AcademicMethodRepo academicMethodRepo;
    private final ResearchWorkRepo researchWorkRepo;
    private final EducationalWorkRepo educationalWorkRepo;
    private final SocialWorkRepo socialWorkRepo;
    private final KpiRepo kpiRepo;
    private final KpiSectionRepo kpiSectionRepo;

    public void createPlan(Plan plan){
        planRepo.save(plan);
    }
    public List<Plan> getPlanByCreatedBy(User user){
        return planRepo.findAllByCreatedBy(user);
    }

    public List<Plan> getPlanByCreatedFor(User user){
        return planRepo.findAllByCreatedFor(user);
    }

    public List<Plan> getPlansByCreatedForAndStatus(User director, String status) {
        return planRepo.getPlansByCreatedForAndStatusEquals(director, status);
    }

    public void save(Plan plan) {
        planRepo.save(plan);
    }

    public void addAcademicWorks(AddAcademicWork addAcademicWork) {
        Plan myPlan = planRepo.getById(addAcademicWork.getIdPlan());
        List<AcademicWork> myAcademicWork = myPlan.getAcademicWorks();
        AcademicWork newAcademicwork = academicWorkRepo.save(new AcademicWork(addAcademicWork.getNameOfDiscipline(), addAcademicWork.getCourse(), addAcademicWork.getTrimester(), addAcademicWork.getGroups(), addAcademicWork.getLecturesPlan(), addAcademicWork.getLecturesFact(), addAcademicWork.getPracticesPlan(), addAcademicWork.getPracticesFact(), addAcademicWork.getHoursPlan(), addAcademicWork.getHoursFact(), addAcademicWork.getTotalPlan(), addAcademicWork.getTotalFact()));
        myAcademicWork.add(newAcademicwork);
        myPlan.setAcademicWorks(myAcademicWork);
        planRepo.save(myPlan);
    }

    public Plan getLastMyPlan(User user){
        List<Plan> plans = planRepo.findAllByCreatedByOrderByIdDesc(user);
        Plan plan =  plans.get(0);
        return plan;
    }

    public void addAcademicMethods(AddAcademicMethod addAcademicMethod) {
        Plan myPlan = planRepo.getById(addAcademicMethod.getIdPlan());
        List<AcademicMethod> myAcademicMethods = myPlan.getAcademicMethods();
        AcademicMethod newAcademicMethod = academicMethodRepo.save(new AcademicMethod(addAcademicMethod.getDiscipline(), addAcademicMethod.getNameWork(), addAcademicMethod.getDeadlines(), addAcademicMethod.getInfoImplementation(), addAcademicMethod.getComment()));
        myAcademicMethods.add(newAcademicMethod);
        myPlan.setAcademicMethods(myAcademicMethods);
        planRepo.save(myPlan);
    }

    public void addResearchWork(AddReseachWork addReseachWork) {
        Plan myPlan = planRepo.getById(addReseachWork.getIdPlan());
        List<ResearchWork> myResearchWork = myPlan.getResearchWorks();
        ResearchWork newResearchWork =researchWorkRepo.save(new ResearchWork(addReseachWork.getNameOfTheWork(),addReseachWork.getDeadlines(), addReseachWork.getInfoImplementation(), addReseachWork.getResults(), addReseachWork.getComments()));
//        if (addReseachWork.getTypeOfTheWork() == 1 || addReseachWork.getTypeOfTheWork() == 2 || addReseachWork.getTypeOfTheWork() == 3) {
//            newResearchWork = researchWorkRepo.save(new ResearchWork((addReseachWork.getTypeOfTheWork()), addReseachWork.getText1(), addReseachWork.getText2(), addReseachWork.getText3(), addReseachWork.getDeadlines(), addReseachWork.getInformationOnImplementation(), addReseachWork.getResults(), addReseachWork.getComments()));
//        } else {
//            newResearchWork = researchWorkRepo.save(new ResearchWork(addReseachWork.getTypeOfTheWork(), addReseachWork.getText1(), addReseachWork.getDeadlines(), addReseachWork.getInformationOnImplementation(), addReseachWork.getResults(), addReseachWork.getComments()));
//        }
        myResearchWork.add(newResearchWork);
        myPlan.setResearchWorks(myResearchWork);
        planRepo.save(myPlan);
    }

    public void addEducationalWork(AddEducationalWork addEducationalWork) {
        Plan myPlan = planRepo.getById(addEducationalWork.getIdPlan());
        List<EducationalWork> myEducationalWork = myPlan.getEducationalWorks();
        EducationalWork newEducationalWork = educationalWorkRepo.save(new EducationalWork(addEducationalWork.getNameOfTheWork(), addEducationalWork.getDeadlines(), addEducationalWork.getInfoImplementation(), addEducationalWork.getResults(), addEducationalWork.getComments()));
        myEducationalWork.add(newEducationalWork);
        myPlan.setEducationalWorks(myEducationalWork);
        planRepo.save(myPlan);
    }

    public void addSocialWork(AddSocialWork addSocialWork) {
        Plan myPlan = planRepo.getById(addSocialWork.getIdPlan());
        List<SocialWork> mySocialWork = myPlan.getSocialWorks();
        SocialWork newSocialWork = socialWorkRepo.save(new SocialWork(addSocialWork.getNameOfTheWork(), addSocialWork.getDeadlines(), addSocialWork.getInfoImplementation(), addSocialWork.getResults(), addSocialWork.getComments()));
        mySocialWork.add(newSocialWork);
        myPlan.setSocialWorks(mySocialWork);
        planRepo.save(myPlan);
    }

    public void addKpi(Long planId, Long idSection,KPI kpi) {
        Plan myPlan = planRepo.getById(planId);
        List<KPI> kpis = myPlan.getKpis();
        kpi.setKpiSection(kpiSectionRepo.findById(idSection).get());
        KPI newKpi = kpiRepo.save(kpi);
        kpis.add(kpi);
        myPlan.setKpis(kpis);
        planRepo.save(myPlan);
    }

    public Plan getPlanById(Long id){
        Plan myPlan = planRepo.findById(id).get();
        return myPlan;
    }

    public void changeYear(Plan plan, String year){
        plan.setYear(year);
        planRepo.save(plan);
    }

    public void deletePlanById(Long id){
        Plan plan = getPlanById(id);
        planRepo.delete(plan);
    }

    public void createExcel(OutputStream outputStream, Plan plan) throws IOException {
//    public void createExcel(Plan plan) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        List<String> tableSheets = Arrays.asList("1 УЧЕБНАЯ РАБОТА " + plan.getYear(), "2 УЧЕБНО-МЕТОДИЧЕСКАЯ РАБОТА",
                "3 НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА", "4 ВОСПИТАТЕЛЬНАЯ РАБОТА", "5 ОБЩЕСТВЕННАЯ РАБОТА", "6 KPI");

        for (String tableName: tableSheets) {
            Sheet sheet = workbook.createSheet(tableName);

            CellStyle wrapTextStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontName("Times New Roman");
            font.setFontHeightInPoints((short) 11);
            wrapTextStyle.setFont(font);
            wrapTextStyle.setWrapText(true);

            Row headerRow = sheet.createRow(0);

            if (tableName.contains("УЧЕБНАЯ РАБОТА")) {
                sheet.setColumnWidth(0, 15 * 256);

                List<String> headerCellValue = Arrays.asList(
                        "Наименование дисциплины", "Курс", "Трим-тр", "Группа", "Лекция", "Практика", "Офис. часы", "Всего часов"
                );

                int k = 4;
                for (int i = 0; i < 12; i++) {

                    if (i >= 1){
                        sheet.setColumnWidth(i, 8 * 256);
                    }
                    sheet.autoSizeColumn(i);

                    Cell cell = headerRow.createCell(i);
                    cell.setCellStyle(wrapTextStyle);

                    if (i >= 4){
                        if (i % 2 == 0){
                            cell.setCellValue(headerCellValue.get(k));
                        } else {
                            sheet.addMergedRegion(new CellRangeAddress(0, 0, i-1, i));
                            k++;
                        }
                    } else {
                        cell.setCellValue(headerCellValue.get(i));
                    }
                }

                Row secondHeaderRow = sheet.createRow(1);
                for (int i = 0; i < 4; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
                }

                CellStyle blueBackgroundCellStyle = sheet.getWorkbook().createCellStyle();
                blueBackgroundCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
                blueBackgroundCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                for (int i = 4; i < 12; i++) {
                    Cell cell = secondHeaderRow.createCell(i);
                    if (i % 2 == 0){
                        cell.setCellValue("План");
                    } else {
                        cell.setCellValue("Факт");
                        cell.setCellStyle(blueBackgroundCellStyle);
                    }
                }

                int rowIndex = 2;
                if (!plan.getAcademicWorks().isEmpty()){
                    int sumLecturesPlan = 0, sumLecturesFact = 0, sumPractiePlan = 0, sumPracticeFact = 0,
                            sumHoursPlan = 0, sumHoursFact = 0, sumTotalPlan = 0, sumTotalFact = 0;

                    for (AcademicWork academicWork: plan.getAcademicWorks()) {
                        Row dataRow = sheet.createRow(rowIndex++);

                        Cell name = dataRow.createCell(0);
                        name.setCellValue(academicWork.getNameOfDiscipline());
                        name.setCellStyle(wrapTextStyle);

                        Cell course = dataRow.createCell(1);
                        course.setCellValue(academicWork.getCourse());

                        Cell trimester = dataRow.createCell(2);
                        trimester.setCellValue(academicWork.getTrimester());

                        Cell group = dataRow.createCell(3);
                        group.setCellValue(academicWork.getGroups());

                        Cell lecturesPlan = dataRow.createCell(4);
                        lecturesPlan.setCellValue(academicWork.getLecturesPlan());
                        sumLecturesPlan += Integer.parseInt(academicWork.getLecturesPlan());

                        Cell lecturesFact = dataRow.createCell(5);
                        lecturesFact.setCellValue(academicWork.getLecturesFact());
                        lecturesFact.setCellStyle(blueBackgroundCellStyle);
                        sumLecturesFact += Integer.parseInt(academicWork.getLecturesFact());

                        Cell practicePlan = dataRow.createCell(6);
                        practicePlan.setCellValue(academicWork.getPracticesPlan());
                        sumPractiePlan += Integer.parseInt(academicWork.getPracticesPlan());


                        Cell practiceFact = dataRow.createCell(7);
                        practiceFact.setCellValue(academicWork.getPracticesFact());
                        practiceFact.setCellStyle(blueBackgroundCellStyle);
                        sumPracticeFact += Integer.parseInt(academicWork.getPracticesFact());

                        Cell officePlan = dataRow.createCell(8);
                        officePlan.setCellValue(academicWork.getHoursPlan());
                        sumHoursPlan += Integer.parseInt(academicWork.getHoursPlan());


                        Cell officeFact = dataRow.createCell(9);
                        officeFact.setCellValue(academicWork.getHoursFact());
                        officeFact.setCellStyle(blueBackgroundCellStyle);
                        sumHoursFact += Integer.parseInt(academicWork.getHoursFact());

                        Cell totalPlan = dataRow.createCell(10);
                        totalPlan.setCellValue(academicWork.getTotalPlan());
                        sumTotalPlan += Integer.parseInt(academicWork.getTotalPlan());

                        Cell totalFact = dataRow.createCell(11);
                        totalFact.setCellValue(academicWork.getTotalFact());
                        totalFact.setCellStyle(blueBackgroundCellStyle);
                        sumTotalFact += Integer.parseInt(academicWork.getTotalFact());

                    }

                    Row dataRow = sheet.createRow(rowIndex++);

                    Cell name = dataRow.createCell(0);
                    name.setCellValue("ИТОГО");

                    Cell lecturesPlan = dataRow.createCell(4);
                    lecturesPlan.setCellValue(sumLecturesPlan);

                    Cell lecturesFact = dataRow.createCell(5);
                    lecturesFact.setCellValue(sumLecturesFact);
                    lecturesFact.setCellStyle(blueBackgroundCellStyle);

                    Cell practicePlan = dataRow.createCell(6);
                    practicePlan.setCellValue(sumPractiePlan);

                    Cell practiceFact = dataRow.createCell(7);
                    practiceFact.setCellValue(sumPracticeFact);
                    practiceFact.setCellStyle(blueBackgroundCellStyle);

                    Cell officePlan = dataRow.createCell(8);
                    officePlan.setCellValue(sumHoursPlan);

                    Cell officeFact = dataRow.createCell(9);
                    officeFact.setCellValue(sumHoursFact);
                    officeFact.setCellStyle(blueBackgroundCellStyle);

                    Cell totalPlan = dataRow.createCell(10);
                    totalPlan.setCellValue(sumTotalPlan);

                    Cell totalFact = dataRow.createCell(11);
                    totalFact.setCellValue(sumTotalFact);
                    totalFact.setCellStyle(blueBackgroundCellStyle);

                }

            }
            else if (tableName.contains("УЧЕБНО-МЕТОДИЧЕСКАЯ РАБОТА")){
                List<String> headerCellValue = Arrays.asList(
                        "№", "Наименование дисциплины", "Наименование работы (силлабус / метод.указание)",
                        "Сроки выполнения", "Информация о выполнении", "Примечание"
                );

                for (int i = 0; i < headerCellValue.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headerCellValue.get(i));
                    cell.setCellStyle(wrapTextStyle);

                    sheet.setColumnWidth(i, 12 * 256);
                    sheet.autoSizeColumn(i);
                }

                int rowIndex = 1;
                int workNum = 0;
                if (!plan.getAcademicMethods().isEmpty()) {
                    for (AcademicMethod academicMethod: plan.getAcademicMethods()) {
                        Row dataRow = sheet.createRow(rowIndex++);

                        Cell num = dataRow.createCell(0);
                        num.setCellValue(++workNum);

                        Cell discipline = dataRow.createCell(1);
                        discipline.setCellValue(academicMethod.getDiscipline());
                        discipline.setCellStyle(wrapTextStyle);

                        Cell nameWork = dataRow.createCell(2);
                        nameWork.setCellValue(academicMethod.getNameWork());
                        nameWork.setCellStyle(wrapTextStyle);

                        Cell deadlines = dataRow.createCell(3);
                        deadlines.setCellValue(academicMethod.getDeadlines());
                        deadlines.setCellStyle(wrapTextStyle);

                        Cell infoImpl = dataRow.createCell(4);
                        infoImpl.setCellValue(academicMethod.getInfoImplementation());
                        infoImpl.setCellStyle(wrapTextStyle);

                        Cell comment = dataRow.createCell(5);
                        comment.setCellValue(academicMethod.getComment());
                        comment.setCellStyle(wrapTextStyle);
                    }
                }
            }
            else {
                List<String> headerCellValue = Arrays.asList(
                        "№", "Наименование работы", "Сроки выполнения",
                        "Результат", "Информация о выполнении", "Примечание"
                );

                for (int i = 0; i < headerCellValue.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headerCellValue.get(i));
                    cell.setCellStyle(wrapTextStyle);

                    sheet.setColumnWidth(i, 12 * 256);
                    sheet.autoSizeColumn(i);
                }

                int rowIndex = 1;
                int workNum = 0;
                
                switch (tableName){
                    case "3 НАУЧНО-ИССЛЕДОВАТЕЛЬСКАЯ РАБОТА":
                        if (!plan.getResearchWorks().isEmpty()){
                            for (ResearchWork research: plan.getResearchWorks()) {
                                outputSections(sheet, rowIndex++, workNum++, research.getNameOfTheWork(),
                                        research.getDeadlines(), research.getResults(), research.getInfoImplementation(),
                                        research.getComments());

                            }
                        }

                        break;
                    case "4 ВОСПИТАТЕЛЬНАЯ РАБОТА":
                        if (!plan.getEducationalWorks().isEmpty()){
                            for (EducationalWork educationalWork: plan.getEducationalWorks()) {
                                outputSections(sheet, rowIndex++, workNum++, educationalWork.getNameOfTheWork(),
                                        educationalWork.getDeadlines(), educationalWork.getResults(), educationalWork.getInfoImplementation(),
                                        educationalWork.getComments());

                            }
                        }
                        break;
                    case "5 ОБЩЕСТВЕННАЯ РАБОТА":
                        if (!plan.getSocialWorks().isEmpty()){
                            for (SocialWork socialWork: plan.getSocialWorks()) {
                                outputSections(sheet, rowIndex++, workNum++, socialWork.getNameOfTheWork(),
                                        socialWork.getDeadlines(), socialWork.getResults(), socialWork.getInfoImplementation(),
                                        socialWork.getComments());

                            }
                        }

                        break;
                    case "6 KPI":
                        if (!plan.getKpis().isEmpty()) {
                            for (KPI kpi: plan.getKpis()) {
                                outputSections(sheet, rowIndex++, workNum++, kpi.getNameOfTheWork(),
                                        kpi.getDeadlines(), kpi.getResults(), kpi.getInformationOnImplementation(),
                                        kpi.getComments());

                            }
                        }

                        break;
                }
            }
        }

        //TODO: РАСКОММЕНЬТЕ И ЗАКОМЕНТИТЕ СОЗДАНИЕ ФАЙЛА. НАСТРОЙТЕ ПРЯМОЕ СКАЧИВАНИЕ
        //Я ОСТАВИЛА ЭТО КАК ДЛЯ ПРОВЕРКИ
//        FileOutputStream out = new FileOutputStream("plan.xlsx");
//        workbook.write(out);
//        out.close();

        workbook.write(outputStream);
        outputStream.flush();

    }

    public void outputSections(Sheet sheet, int rowIndex, int workNum, 
                               String name, String deadlines, String results, String infoImpl, String comment){
        
        Row dataRow = sheet.createRow(rowIndex);
        CellStyle wrapTextStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 11);
        wrapTextStyle.setFont(font);
        wrapTextStyle.setWrapText(true);

        Cell num = dataRow.createCell(0);
        num.setCellValue(++workNum);
        num.setCellStyle(wrapTextStyle);

        Cell nameOfWork = dataRow.createCell(1);
        nameOfWork.setCellValue(name);
        nameOfWork.setCellStyle(wrapTextStyle);

        Cell ddl = dataRow.createCell(2);
        ddl.setCellValue(deadlines);
        ddl.setCellStyle(wrapTextStyle);

        Cell res = dataRow.createCell(3);
        res.setCellValue(results);
        res.setCellStyle(wrapTextStyle);

        Cell info = dataRow.createCell(4);
        info.setCellValue(infoImpl);
        info.setCellStyle(wrapTextStyle);

        Cell comm = dataRow.createCell(5);
        comm.setCellValue(comment);
        comm.setCellStyle(wrapTextStyle);
    }
}
