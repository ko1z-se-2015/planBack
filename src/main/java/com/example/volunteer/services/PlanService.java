package com.example.volunteer.services;

import com.example.volunteer.entities.*;
import com.example.volunteer.entities.kpi_sections.KpiSection;
import com.example.volunteer.modules.*;
import com.example.volunteer.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

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

    private final DepartmentService departmentService;

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

//    public void createReportDocx(OutputStream outputStream, Plan plan) throws IOException {
//        File templateFile = new File("templateReport.docx");
//        FileInputStream templateStream = new FileInputStream(templateFile);
//
//        XWPFDocument document = new XWPFDocument(templateStream);
//
//        int year = LocalDate.now().getYear();
//        User teacher = plan.getCreatedBy();
//        String teacherFullName = String.format("%s %s %s", teacher.getLastName(), teacher.getFirstName(), teacher.getMiddleName());
//        String teacherSurname = String.format("%s %s.%s.",
//                teacher.getLastName(), teacher.getFirstName().charAt(0), teacher.getMiddleName().charAt(0));
//
//
//        Map<String, String> placeholderMap = new HashMap<>();
//        placeholderMap.put("{{currentYear}}", Integer.toString(year));
//        placeholderMap.put("{{teacherFullName}}", teacherFullName);
//        placeholderMap.put("{{planYear}}", plan.getYear());
//        placeholderMap.put("teacherInitials", teacherSurname);
//
//        List<XWPFTable> tables = document.getTables();
//
//        for (int i = 0; i < tables.size(); i++) {
//
//            switch (i){
//                case 0:
//                    for (AcademicWork academicWork: plan.getAcademicWorks()) {
//                        XWPFTable table = tables.get(i);
//                        XWPFTableRow row = table.createRow();
//
//                        row.getCell(0).setText(academicWork.getNameOfDiscipline());
//                        row.getCell(1).setText(academicWork.getCourse());
//                        row.getCell(2).setText(academicWork.getTrimester());
//                        row.getCell(3).setText(academicWork.getGroups());
//
//                        row.getCell(4).setText(academicWork.getLecturesPlan() + "/" + academicWork.getLecturesFact());
//                        row.getCell(5).setText(academicWork.getPracticesPlan() + "/" + academicWork.getPracticesFact());
//                        row.getCell(6).setText(academicWork.getHoursPlan() + "/" + academicWork.getHoursFact());
//                        row.getCell(7).setText(academicWork.getTotalPlan() + "/" + academicWork.getTotalFact());
//                    }
//
//                    break;
//
//                case 1:
//                    int rowNumberAcademic = 0;
//
//                    for (AcademicMethod academicMethod: plan.getAcademicMethods()) {
//                        XWPFTableRow row = tables.get(i).createRow();
//
//                        row.getCell(0).setText(String.valueOf(++rowNumberAcademic));
//                        row.getCell(1).setText(academicMethod.getDiscipline());
//                        row.getCell(2).setText(academicMethod.getNameWork());
//                        row.getCell(3).setText(academicMethod.getDeadlines());
//                        row.getCell(4).setText(academicMethod.getComment());
//
//                    }
//                    break;
//
//                case 2:
//                    int rowNumberResearch = 0;
//
//                    for (ResearchWork researchWork: plan.getResearchWorks()) {
//                        XWPFTableRow row = tables.get(i).createRow();
//                        row.getCell(0).setText(String.valueOf(++rowNumberResearch));
//                        row.getCell(1).setText(researchWork.getNameOfTheWork());
//                        row.getCell(2).setText(researchWork.getDeadlines());
//                        row.getCell(3).setText(researchWork.getResults());
//                        row.getCell(4).setText(researchWork.getComments());
//                    }
//                    break;
//                case 3:
//                    int rowNumberEducation = 0;
//
//                    for (EducationalWork educationalWork: plan.getEducationalWorks()) {
//                        XWPFTableRow row = tables.get(i).createRow();
//                        row.getCell(0).setText(String.valueOf(++rowNumberEducation));
//                        row.getCell(1).setText(educationalWork.getNameOfTheWork());
//                        row.getCell(2).setText(educationalWork.getDeadlines());
//                        row.getCell(3).setText(educationalWork.getResults());
//                        row.getCell(4).setText(educationalWork.getComments());
//                    }
//                    break;
//                case 4:
//                    int rowNumberSocial = 0;
//
//                    for (SocialWork socialWork: plan.getSocialWorks()) {
//                        XWPFTableRow row = tables.get(i).createRow();
//                        row.getCell(0).setText(String.valueOf(++rowNumberSocial));
//                        row.getCell(1).setText(socialWork.getNameOfTheWork());
//                        row.getCell(2).setText(socialWork.getDeadlines());
//                        row.getCell(3).setText(socialWork.getResults());
//                        row.getCell(4).setText(socialWork.getComments());
//                    }
//                    break;
//            }
//        }
//
//        for (XWPFParagraph paragraph : document.getParagraphs()) {
//            for (XWPFRun run : paragraph.getRuns()) {
//                String text = run.getText(0);
//                if (text != null) {
//                    for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
//                        if (text.contains(entry.getKey())) {
//                            text = text.replace(entry.getKey(), entry.getValue());
//                            run.setText(text, 0);
//                        }
//                    }
//                    run.setFontFamily("Times New Roman");
//                }
//            }
//        }
//
//        document.write(outputStream);
//        outputStream.close();
//    }

    public void createPlanDocx(OutputStream outputStream, Plan plan) throws IOException {
        File templateFile = new File("templateAll.docx");
        FileInputStream templateStream = new FileInputStream(templateFile);

        XWPFDocument document = new XWPFDocument(templateStream);

        int year = LocalDate.now().getYear();
        User teacher = plan.getCreatedBy();
        String teacherFullName = String.format("%s %s %s", teacher.getLastName(), teacher.getFirstName(), teacher.getMiddleName());
        String teacherSurname = String.format("%s %s.%s.",
                teacher.getLastName(), teacher.getFirstName().charAt(0), teacher.getMiddleName().charAt(0));

        User director = plan.getCreatedFor();
        String directorSurname = String.format("%s %s.%s.",
                director.getLastName(), director.getFirstName().charAt(0), director.getMiddleName().charAt(0));

        String departmentName = departmentService.getDepartmentByTeacher(teacher).getName();
        String docxName = plan.isReport() ?
                "ОТЧЕТ О ВЫПОЛНЕНИИ ИНДИВИДУАЛЬНОГО ПЛАНА РАБОТЫ ПРЕПОДАВАТЕЛЯ" : "ИНДИВИДУАЛЬНЫЙ ПЛАН РАБОТЫ ПРЕПОДАВАТЕЛЯ";

        Map<String, String> placeholderMap = new HashMap<>();
        placeholderMap.put("{{currentYear}}", Integer.toString(year));
        placeholderMap.put("{{teacherFullName}}", teacherFullName);
        placeholderMap.put("{{planYear}}", plan.getYear());
        placeholderMap.put("{{teacherInitials}}", teacherSurname);
        placeholderMap.put("departmentName", departmentName);
        placeholderMap.put("directorInitials", directorSurname);
        placeholderMap.put("{{name}}", docxName);

        List<XWPFTable> tables = document.getTables();

        for (int i = 1; i < tables.size(); i++) {

            switch (i){
                case 1:
                    int lecturesP = 0, lecturesF = 0, practiceP = 0, practiceF = 0,
                            hoursP = 0, hoursF = 0, totalP = 0, totalF = 0;

                    for (AcademicWork academicWork: plan.getAcademicWorks()) {
                        XWPFTable table = tables.get(i);
                        XWPFTableRow row = table.createRow();

                        row.createCell();
                        row.createCell();
                        row.createCell();
                        row.createCell();

                        row.getCell(0).setText(academicWork.getNameOfDiscipline());
                        row.getCell(1).setText(academicWork.getCourse());
                        row.getCell(2).setText(academicWork.getTrimester());
                        row.getCell(3).setText(academicWork.getGroups());

                        row.getCell(4).setText(academicWork.getLecturesPlan());
                        row.getCell(5).setText(academicWork.getLecturesFact());
                        lecturesP += isEmptyOrWhitespace(academicWork.getLecturesPlan()) ? 0 : Integer.parseInt(academicWork.getLecturesPlan());
                        lecturesF += isEmptyOrWhitespace(academicWork.getLecturesFact()) ? 0 : Integer.parseInt(academicWork.getLecturesFact());

                        row.getCell(6).setText(academicWork.getPracticesPlan());
                        row.getCell(7).setText(academicWork.getPracticesFact());
                        practiceP += isEmptyOrWhitespace(academicWork.getPracticesPlan()) ? 0 : Integer.parseInt(academicWork.getPracticesPlan());
                        practiceF += isEmptyOrWhitespace(academicWork.getPracticesFact()) ? 0 : Integer.parseInt(academicWork.getPracticesFact());

                        row.getCell(8).setText(academicWork.getHoursPlan());
                        row.getCell(9).setText(academicWork.getHoursFact());
                        hoursP += isEmptyOrWhitespace(academicWork.getHoursPlan()) ? 0 : Integer.parseInt(academicWork.getHoursPlan());
                        hoursF += isEmptyOrWhitespace(academicWork.getHoursFact()) ? 0 : Integer.parseInt(academicWork.getHoursFact());

                        row.getCell(10).setText(academicWork.getTotalPlan());
                        row.getCell(11).setText(academicWork.getTotalFact());
                        totalP += isEmptyOrWhitespace(academicWork.getTotalPlan()) ? 0 : Integer.parseInt(academicWork.getTotalPlan());
                        totalF += isEmptyOrWhitespace(academicWork.getTotalFact()) ? 0 : Integer.parseInt(academicWork.getTotalFact());


                        setBckgColorTableDocx(row);
                    }

                    XWPFTableRow rowSum = tables.get(i).createRow();

                    rowSum.createCell();
                    rowSum.createCell();
                    rowSum.createCell();
                    rowSum.createCell();

                    rowSum.getCell(0).setText("ИТОГО");

                    rowSum.getCell(4).setText(String.valueOf(lecturesP));
                    rowSum.getCell(5).setText(String.valueOf(lecturesF));

                    rowSum.getCell(6).setText(String.valueOf(practiceP));
                    rowSum.getCell(7).setText(String.valueOf(practiceF));

                    rowSum.getCell(8).setText(String.valueOf(hoursP));
                    rowSum.getCell(9).setText(String.valueOf(hoursF));

                    rowSum.getCell(10).setText(String.valueOf(totalP));
                    rowSum.getCell(11).setText(String.valueOf(totalF));

                    setBckgColorTableDocx(rowSum);

                    break;

                case 2:
                    int rowNumberAcademic = 0;

                    for (AcademicMethod academicMethod: plan.getAcademicMethods()) {
                        XWPFTableRow row = tables.get(i).createRow();

                        row.getCell(0).setText(String.valueOf(++rowNumberAcademic));
                        row.getCell(1).setText(academicMethod.getDiscipline());
                        row.getCell(2).setText(academicMethod.getNameWork());
                        row.getCell(3).setText(academicMethod.getDeadlines());
                        row.getCell(4).setText(academicMethod.getInfoImplementation());
                        row.getCell(5).setText(academicMethod.getComment());

                    }
                    break;

                case 3:
                    int rowNumberResearch = 0;

                    for (ResearchWork researchWork: plan.getResearchWorks()) {
                        XWPFTableRow row = tables.get(i).createRow();
                        outputSectionsDocx(row, ++rowNumberResearch,
                                researchWork.getNameOfTheWork(), researchWork.getDeadlines(), researchWork.getResults(),
                                researchWork.getInfoImplementation(), researchWork.getComments());
                    }
                    break;
                case 4:
                    int rowNumberEducation = 0;

                    for (EducationalWork educationalWork: plan.getEducationalWorks()) {
                        XWPFTableRow row = tables.get(i).createRow();
                        outputSectionsDocx(row, ++rowNumberEducation,
                                educationalWork.getNameOfTheWork(), educationalWork.getDeadlines(), educationalWork.getResults(),
                                educationalWork.getInfoImplementation(), educationalWork.getComments());
                    }
                    break;
                case 5:
                    int rowNumberSocial = 0;

                    for (SocialWork socialWork: plan.getSocialWorks()) {
                        XWPFTableRow row = tables.get(i).createRow();
                        outputSectionsDocx(row, ++rowNumberSocial,
                                socialWork.getNameOfTheWork(), socialWork.getDeadlines(), socialWork.getResults(),
                                socialWork.getInfoImplementation(), socialWork.getComments());
                    }
                    break;
                case 6:
                    int rowNumberKpi = 0;

                    for (KPI kpi: plan.getKpis()) {
                        XWPFTableRow row = tables.get(i).createRow();
                        outputSectionsDocx(row, ++rowNumberKpi,
                                kpi.getNameOfTheWork(), kpi.getDeadlines(), kpi.getResults(),
                                kpi.getInformationOnImplementation(), kpi.getComments());
                    }
                    break;
            }
        }

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null) {
                    for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
                        if (text.contains(entry.getKey())) {
                            text = text.replace(entry.getKey(), entry.getValue());
                            run.setText(text, 0);
                        }
                    }
                    run.setFontFamily("Times New Roman");
                }
            }
        }

        document.write(outputStream);
        outputStream.close();
    }

    private boolean isEmptyOrWhitespace(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void setBckgColorTableDocx(XWPFTableRow row) {
        for (int colIndex = 5; colIndex < row.getTableCells().size(); colIndex++) {
            XWPFTableCell cell = row.getCell(colIndex);
            if (colIndex % 2 != 0) {
                CTTc ctTc = cell.getCTTc();
                CTShd ctShd = ctTc.addNewTcPr().addNewShd();
                ctShd.setFill("C1D5EC");
            }
        }
    }

    private void outputSectionsDocx(XWPFTableRow row, int workNum,
                                    String name, String deadlines, String results, String infoImpl, String comment ) {

        row.getCell(0).setText(String.valueOf(workNum));
        row.getCell(1).setText(name);
        row.getCell(2).setText(deadlines);
        row.getCell(3).setText(results);
        row.getCell(4).setText(infoImpl);
        row.getCell(5).setText(comment);
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
                        sumLecturesPlan += academicWork.getLecturesPlan() == null ? 0 : Integer.parseInt(academicWork.getLecturesPlan());

                        Cell lecturesFact = dataRow.createCell(5);
                        lecturesFact.setCellValue(academicWork.getLecturesFact());
                        lecturesFact.setCellStyle(blueBackgroundCellStyle);
                        sumLecturesFact += academicWork.getLecturesFact() == null ? 0 : Integer.parseInt(academicWork.getLecturesFact());

                        Cell practicePlan = dataRow.createCell(6);
                        practicePlan.setCellValue(academicWork.getPracticesPlan());
                        sumPractiePlan += academicWork.getPracticesPlan() == null ? 0 : Integer.parseInt(academicWork.getPracticesPlan());


                        Cell practiceFact = dataRow.createCell(7);
                        practiceFact.setCellValue(academicWork.getPracticesFact());
                        practiceFact.setCellStyle(blueBackgroundCellStyle);
                        sumPracticeFact += academicWork.getPracticesFact() == null ? 0 :  Integer.parseInt(academicWork.getPracticesFact());

                        Cell officePlan = dataRow.createCell(8);
                        officePlan.setCellValue(academicWork.getHoursPlan());
                        sumHoursPlan += academicWork.getHoursPlan() == null ? 0 : Integer.parseInt(academicWork.getHoursPlan());


                        Cell officeFact = dataRow.createCell(9);
                        officeFact.setCellValue(academicWork.getHoursFact());
                        officeFact.setCellStyle(blueBackgroundCellStyle);
                        sumHoursFact += academicWork.getHoursFact() == null ? 0 : Integer.parseInt(academicWork.getHoursFact());

                        Cell totalPlan = dataRow.createCell(10);
                        totalPlan.setCellValue(academicWork.getTotalPlan());
                        sumTotalPlan += academicWork.getTotalPlan() == null ? 0 : Integer.parseInt(academicWork.getTotalPlan());

                        Cell totalFact = dataRow.createCell(11);
                        totalFact.setCellValue(academicWork.getTotalFact());
                        totalFact.setCellStyle(blueBackgroundCellStyle);
                        sumTotalFact += academicWork.getTotalFact() == null ? 0 : Integer.parseInt(academicWork.getTotalFact());

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

//        FileOutputStream out = new FileOutputStream("plan.xlsx");
//        workbook.write(out);
//        out.close();

        workbook.write(outputStream);
        outputStream.flush();

    }

    private void outputSections(Sheet sheet, int rowIndex, int workNum,
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

    public void insertFromDocx(User user, String fileBase64) throws IOException {
        byte[] fileBytes = Base64.getDecoder().decode(fileBase64);
        InputStream inputStream = new ByteArrayInputStream(fileBytes);

        XWPFDocument document = new XWPFDocument(inputStream);

        List<XWPFTable> tables = document.getTables();

        String year = "";
        List<AcademicWork> academicWorks = new ArrayList<>();
        List<AcademicMethod> academicMethods = new ArrayList<>();
        List<ResearchWork> researchWorks = new ArrayList<>();
        List<EducationalWork> educationalWorks = new ArrayList<>();
        List<SocialWork> socialWorks = new ArrayList<>();
        List<KPI> kpis = new ArrayList<>();
        Department department = departmentService.getDepartmentByTeacher(user);
        User director = department.getDirector();
        boolean found = false;

        for (int i = 1; i < document.getParagraphs().size(); i++) {
            XWPFParagraph paragraph = document.getParagraphs().get(i);
            String text = paragraph.getText();

            if (text != null && text.contains("учебный год")) {
                found = true;
                continue;
            }

            if (found) {
                XWPFParagraph previousParagraph = document.getParagraphs().get(i - 1);
                String previousText = previousParagraph.getText();

                if (previousText != null) {
                    year = previousText.trim();
                    year = year.replace(" учебный год", "");
                    break;
                }
            }
        }


        int startAtRow;
        for (int i = 1; i < tables.size(); i++){
            XWPFTable table = tables.get(i);
            if (i == 1) {
                startAtRow = 2;
            } else startAtRow = 1;

            for (int j = startAtRow; j < table.getRows().size(); j++) {
                XWPFTableRow row = table.getRow(j);
                if (row == null) {
                    continue;
                }
                switch (i) {
                    case 1: {
                        XWPFTableCell cell0 = row.getCell(0);
                        if (cell0 == null || cell0.getText() == null || cell0.getText().toLowerCase().contains("итого")) {
                            continue;
                        }

                        AcademicWork academicWork = new AcademicWork();
                        academicWork.setNameOfDiscipline(cell0.getText());
                        academicWork.setCourse(getCellText(row, 1));
                        academicWork.setTrimester(getCellText(row, 2));
                        academicWork.setGroups(getCellText(row, 3));
                        academicWork.setLecturesPlan(getCellText(row, 4));
                        academicWork.setLecturesFact(getCellText(row, 5));
                        academicWork.setPracticesPlan(getCellText(row, 6));
                        academicWork.setPracticesFact(getCellText(row, 7));
                        academicWork.setHoursPlan(getCellText(row, 8));
                        academicWork.setHoursFact(getCellText(row, 9));
                        academicWork.setTotalPlan(getCellText(row, 10));
                        academicWork.setTotalFact(getCellText(row, 11));

                        academicWorkRepo.save(academicWork);
                        academicWorks.add(academicWork);
                        break;
                    }
                    case 2: {
                        AcademicMethod academicMethod = new AcademicMethod();
                        academicMethod.setDiscipline(getCellText(row, 1));
                        academicMethod.setNameWork(getCellText(row, 2));
                        academicMethod.setDeadlines(getCellText(row, 3));
                        academicMethod.setInfoImplementation(getCellText(row, 4));
                        academicMethod.setComment(getCellText(row, 5));

                        academicMethodRepo.save(academicMethod);
                        academicMethods.add(academicMethod);
                        break;
                    }
                    case 3: {
                        ResearchWork researchWork = new ResearchWork();
                        researchWork.setNameOfTheWork(getCellText(row, 1));
                        researchWork.setDeadlines(getCellText(row, 2));
                        researchWork.setResults(getCellText(row, 3));
                        researchWork.setInfoImplementation(getCellText(row, 4));
                        researchWork.setComments(getCellText(row, 5));

                        researchWorkRepo.save(researchWork);
                        researchWorks.add(researchWork);
                        break;
                    }
                    case 4: {
                        EducationalWork educationalWork = new EducationalWork();
                        educationalWork.setNameOfTheWork(getCellText(row, 1));
                        educationalWork.setDeadlines(getCellText(row, 2));
                        educationalWork.setResults(getCellText(row, 3));
                        educationalWork.setInfoImplementation(getCellText(row, 4));
                        educationalWork.setComments(getCellText(row, 5));

                        educationalWorkRepo.save(educationalWork);
                        educationalWorks.add(educationalWork);
                        break;
                    }
                    case 5: {
                        SocialWork socialWork = new SocialWork();
                        socialWork.setNameOfTheWork(getCellText(row, 1));
                        socialWork.setDeadlines(getCellText(row, 2));
                        socialWork.setResults(getCellText(row, 3));
                        socialWork.setInfoImplementation(getCellText(row, 4));
                        socialWork.setComments(getCellText(row, 5));

                        socialWorkRepo.save(socialWork);
                        socialWorks.add(socialWork);
                        break;
                    }
                    case 6: {
                        KPI kpi = new KPI();
                        kpi.setNameOfTheWork(getCellText(row, 1));
                        kpi.setDeadlines(getCellText(row, 2));
                        kpi.setResults(getCellText(row, 3));
                        kpi.setInformationOnImplementation(getCellText(row, 4));
                        kpi.setComments(getCellText(row, 5));

                        KpiSection kpiSection = kpiSectionRepo.getByPositionAndDegreeAndOptionsContains(
                                user.getPosition(), user.getDegree(), kpi.getNameOfTheWork());
                        if (kpiSection == null) {
                            continue;
                        }

                        kpi.setKpiSection(kpiSection);
                        kpiRepo.save(kpi);
                        kpis.add(kpi);
                        break;
                    }
                }
            }
        }

        planRepo.save(new Plan(
                year, academicWorks, academicMethods, researchWorks,
                educationalWorks, socialWorks, kpis, user, director));

        inputStream.close();
    }

    private String getCellText(XWPFTableRow row, int cellIndex) {
        XWPFTableCell cell = row.getCell(cellIndex);
        if (cell == null || cell.getText() == null) {
            return null;
        }
        return cell.getText();
    }
}
