package youthshelter.youth.co.kr.Util;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Response_Culture_Info {

    @SerializedName("list_total_count")
    private Integer listTotalCount;

    @SerializedName("RESULT")
    private Result rESULT;

    public Integer getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(Integer listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public Result getrESULT() {
        return rESULT;
    }

    public void setrESULT(Result rESULT) {
        this.rESULT = rESULT;
    }

    @SerializedName("row")
    public ArrayList<Row> row = new ArrayList<>();

    public ArrayList<Row> getRow() {
        return row;
    }

    public void setRow(ArrayList<Row> row) {
        this.row = row;
    }

    public class Row {
        @SerializedName("CULTCODE")
        private int CULTCODE;
        @SerializedName("SUBJCODE")
        private int SUBJCODE;
        @SerializedName("CODENAME")
        private String CODENAME;
        @SerializedName("TITLE")
        private String TITLE;
        @SerializedName("STRTDATE")
        private String STRTDATE;
        @SerializedName("END_DATE")
        private String END_DATE;
        @SerializedName("TIME")
        private String TIME;
        @SerializedName("PLACE")
        private String PLACE;
        @SerializedName("ORG_LINK")
        private String ORG_LINK;
        @SerializedName("MAIN_IMG")
        private String MAIN_IMG;
        @SerializedName("INQUIRY")
        private String INQUIRY;
        @SerializedName("GCODE")
        private String GCODE;
        @SerializedName("PROGRAM")
        private String PROGRAM;

        public String getPROGRAM() {
            return PROGRAM;
        }

        public void setPROGRAM(String PROGRAM) {
            this.PROGRAM = PROGRAM;
        }

        public String getGCODE() {
            return GCODE;
        }

        public void setGCODE(String GCODE) {
            this.GCODE = GCODE;
        }

        public String getINQUIRY() {
            return INQUIRY;
        }

        public void setINQUIRY(String INQUIRY) {
            this.INQUIRY = INQUIRY;
        }

        public String getMAIN_IMG() {
            return MAIN_IMG;
        }

        public void setMAIN_IMG(String MAIN_IMG) {
            this.MAIN_IMG = MAIN_IMG;
        }

        public int getCULTCODE() {
            return CULTCODE;
        }

        public void setCULTCODE(int CULTCODE) {
            this.CULTCODE = CULTCODE;
        }

        public int getSUBJCODE() {
            return SUBJCODE;
        }

        public void setSUBJCODE(int SUBJCODE) {
            this.SUBJCODE = SUBJCODE;
        }

        public String getCODENAME() {
            return CODENAME;
        }

        public void setCODENAME(String CODENAME) {
            this.CODENAME = CODENAME;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getSTRTDATE() {
            return STRTDATE;
        }

        public void setSTRTDATE(String STRTDATE) {
            this.STRTDATE = STRTDATE;
        }

        public String getEND_DATE() {
            return END_DATE;
        }

        public void setEND_DATE(String END_DATE) {
            this.END_DATE = END_DATE;
        }

        public String getTIME() {
            return TIME;
        }

        public void setTIME(String TIME) {
            this.TIME = TIME;
        }

        public String getPLACE() {
            return PLACE;
        }

        public void setPLACE(String PLACE) {
            this.PLACE = PLACE;
        }

        public String getORG_LINK() {
            return ORG_LINK;
        }

        public void setORG_LINK(String ORG_LINK) {
            this.ORG_LINK = ORG_LINK;
        }
    }

    public class Result{
        @SerializedName("CODE")
        public String CODE;
        @SerializedName("MESSAGE")
        private String MESSAGE;

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getMESSAGE() {
            return MESSAGE;
        }

        public void setMESSAGE(String MESSAGE) {
            this.MESSAGE = MESSAGE;
        }
    }

    public class Example{
        @SerializedName("SearchConcertDetailService")
        private Response_Culture_Info response_culture_info;

        public Response_Culture_Info getResponse_culture_info() {
            return response_culture_info;
        }

        public void setResponse_culture_info(Response_Culture_Info response_culture_info) {
            this.response_culture_info = response_culture_info;
        }
    }
}

