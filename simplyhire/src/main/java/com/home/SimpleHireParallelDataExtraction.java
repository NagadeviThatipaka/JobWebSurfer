package com.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SimpleHireParallelDataExtraction {


    public static void main(String[] args) throws Throwable{
        int NO_OF_THREADS = 2;

        List<String> links = SearchJobLinks();

        System.out.println("No. of pages: "+links.size());

        Thread.sleep(10000);

        List<Thread> threads = new ArrayList<>();

        int size = (links.size() + NO_OF_THREADS - 1)/NO_OF_THREADS;
        System.out.println("Links per Thread: "+size);
        int start=0;

        for(int i=0; i<NO_OF_THREADS; i++){
            int end = Math.min(start + size-1, links.size()-1);
            Thread thread = new Thread(new BrowserTask(links, start, end));
            threads.add(thread);
            start = end+1;
        }


        for(Thread thread : threads){
            thread.start();
        }

    }
    public static List<String> SearchJobLinks(){
        int NO_PAGES_TO_PRINT = 4;
        System.setProperty("webdriver.chrome.driver", "I:\\Devi\\ChromeDriver\\chromedriver-win64-131\\chromedriver.exe");

        List<String> links = new ArrayList<>();

        // Initialize the Chrome driver
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless"); // Run in headless mode
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to the website
            driver.get("https://www.simplyhired.co.in/search?q=java&l=hyderabad%2C+telangana&s=d");

            int nextPageNo = 2;

            // Loop through all the pages
            boolean hasNextPage = true;
            while (hasNextPage) {

                if ((nextPageNo - 1) > NO_PAGES_TO_PRINT) {
                    break;
                }
                // Get the current URL
                String currentUrl = driver.getCurrentUrl();

                links.add(currentUrl);

                // Print the current URL
                //System.out.println("The current page URL is: " + currentUrl);

                System.out.println("Page No: " + (nextPageNo - 1));

                String pageXPath = "//a[@aria-label='page " + nextPageNo + "']";

                // Check if the "Next" button exists and is enabled
                try {

                    WebElement div = driver.findElement(By.className("css-ukpd8g"));
                    // Use the correct locator for the "Next" button

                    WebElement nav = div.findElement(By.tagName("nav"));

                    System.out.println("nav: " + nav.getText());

                    WebElement pageElement = nav.findElement(By.xpath(pageXPath));

                    if (pageElement != null) {
                        // Click the "Next" button
                        pageElement.click();

                        // Wait for the next page to load (you might need to wait for a specific element
                        // to be visible)
                        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pageNumberBlockNext")));
                    } else {
                        hasNextPage = false; // No more pages
                    }
                } catch (Exception e) {
                    hasNextPage = false; // No "Next" button found, end of pagination
                    e.printStackTrace();
                }
                Thread.sleep(2000);
                nextPageNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }

        return links;
    }

    private static List<JobDetail> extractDataFromPage(int pageNo, WebDriver driver) {
        List<JobDetail> listOfJobs = new ArrayList<>();
        try {

            List<WebElement> items = driver.findElement(By.id("job-list")).findElements(By.tagName("li"));
            System.out.println("PageNo: "  + pageNo + " - Total Jobs : " + items.size());

            for (int i = 0; i < items.size(); i++) {
                WebElement item = items.get(i);
                item.click();
                Thread.sleep(2000);

                WebElement parentElement = driver.findElement(By.className("css-k008qs"));

                System.out.println("Job No : " + i);

                // Get all child elements of the parent element
                List<WebElement> childElements = parentElement.findElements(By.className("css-1ebprri"));

                // Loop through the child elements and extract the required data
                try {


                    String jobTitle = parentElement.findElement(By.xpath("//aside[@class='css-nzjs22']//header[@class='css-lq62w6']//div[@class='css-10747oj']//div[@class='css-0']//div[@class='chakra-stack css-1iblfv6']//h2")).getText();
                    String company = parentElement.findElement(By.xpath("//aside[@class='css-nzjs22']//header[@class='css-lq62w6']//div[@class='css-10747oj']//div[@class='css-0']//div[2]//div[1]//span[1]//span//span[1]")).getText();
                    String location = parentElement.findElement(By.xpath("//aside[@class='css-nzjs22']//header[@class='css-lq62w6']//div[@class='css-10747oj']//div[@class='css-0']//div[2]//div[1]//span[2]//span//span")).getText();
                    String ptime  =  parentElement.findElement(By.xpath("//aside[@class='css-nzjs22']//div[@class='css-1u3q0w0']//div[@class='css-10747oj'][1]//div[@class='css-0']//div[@class='css-1ebprri'][1]//div[@class='css-bu2sfw']//span//span[@class='chakra-stack css-xyzzkl']")).getText();

                    System.out.println("ptime : "+ ptime);
                    // Convert posted date to local time
                    String localPostedDate = convertPostedDate(ptime);
                    String titleLocation = "";
                    String basicDetails = "";
                    String qualification = "";
                    String fullJobDescription = "";

                    if(childElements.size()>0){
                        titleLocation = childElements.get(0).getText();
                    }

                    if(childElements.size()>1){
                        basicDetails = childElements.get(1).getText();
                    }

                    if(childElements.size()>2){
                        qualification = childElements.get(2).getText();
                    }

                    if(childElements.size()>3){
                        fullJobDescription = childElements.get(3).getText();
                    }

                    JobDetail jobDetail = new JobDetail(titleLocation, basicDetails, qualification, fullJobDescription);
                    jobDetail.setJobTitle(jobTitle);
                    jobDetail.setCompany(company);
                    jobDetail.setJobLocation(location);
                    jobDetail.setTime(localPostedDate);

                    String key = jobDetail.getTime().toString() + "_" + jobDetail.getJobTitle() + "_" + jobDetail.getCompany() + "_" + jobDetail.getJobLocation();

                    jobDetail.setKey(key);

                    listOfJobs.add(jobDetail);

                }catch (Exception ex){
                    ex.printStackTrace();
                }

                System.out.println("===================================================");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfJobs;

    }

    private static String convertPostedDate(String ptime) {
        LocalDateTime postedDate;
        try{

            LocalDateTime now = LocalDateTime.now();


            if(ptime.contains("hour")){
                int hoursAgo =Integer.parseInt(ptime.split(" ")[0]);
                now = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0, 0);
                postedDate= now.minus(hoursAgo, ChronoUnit.HOURS);
            }else if(ptime.contains("day")){
                int daysAgo=Integer.parseInt(ptime.split(" ")[0]);
                now = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
                postedDate= now.minus(daysAgo,ChronoUnit.DAYS);
            }else{ DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                postedDate = LocalDateTime.parse(ptime, inputFormatter);
            }
            // Convert to local timezone
            ZonedDateTime localDateTime = postedDate.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.systemDefault());

            // Format the result
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return localDateTime.format(outputFormatter);

        } catch (Exception e) {
            System.err.println("Error parsing posted time: " + ptime);
            return "Unknown Date";
        }
    }


   static class BrowserTask implements Runnable {

       private List<String> links;
       private int startIndex;
       private int endIndex;


       public BrowserTask(List<String> links, int startIndex, int endIndex) {
           this.links = links;
           this.startIndex = startIndex;
           this.endIndex = endIndex;
       }

       @Override
       public void run() {

           JobDetailsDataService jobDetailsDataService = new JobDetailsDataService();
           // Set the path to the chromedriver executable (make sure it's set correctly)
           System.setProperty("webdriver.chrome.driver", "I:\\Devi\\ChromeDriver\\chromedriver-win64-131\\chromedriver.exe");

           WebDriver driver = new ChromeDriver();

           for (int i = startIndex; i <= endIndex; i++) {
               String url = links.get(i);
               int pageNo = i;
               driver.get(url);

               // Process the page
               System.out.println("Title of the page: " + driver.getTitle());

               // Extract data from the current page
               List<JobDetail> listOfJobs = extractDataFromPage(pageNo, driver);

               jobDetailsDataService.AddJobDetails(listOfJobs);

               // TODO: We can comment or remove this code later.
               for (JobDetail jobDetail : listOfJobs) {
                   System.out.println("Job Title: " + jobDetail.getJobTitle());
                   System.out.println("Company: " + jobDetail.getCompany());
                   System.out.println("Job Location: " + jobDetail.getJobLocation());
                   System.out.println("ptime : " + jobDetail.getTime());

                   System.out.println("key: " + jobDetail.getKey());
               }
           }

           driver.quit();  // Close the browser
       }
   }
}
