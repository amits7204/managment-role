package com.example.jeevanjyoti;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevanjyoti.customSpinner.CustomAdapter;
import com.example.jeevanjyoti.customSpinner.CustomItems;
import com.example.jeevanjyoti.registerencapsulation.UserRegister;
import com.example.jeevanjyoti.retrofit.RetrofitClient;
import com.example.jeevanjyoti.retrofit.UserRegisterApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "RegisterActivity";
    private DatePickerDialog.OnDateSetListener mDateListener;
    private Spinner mMaritalSpinner, mEducationSpinner, mOccupationSpinner, mStateSpinner,
            mDistrictSpinner, mGenderSpinner, mEducationStatusSpinner;
    private EditText mFullName, mFatherName, mMotherName, mMobileNumber, mOccupationEditText,
            mFlatEditText, mBuildingEditText, mAreaEditText, mPinCodeEditText;
    private EditText mDOBEditText;
    Button mRegisterButton;
    ArrayList<CustomItems> mStateCustomList = new ArrayList<>();
    ArrayList<CustomItems> mUpDistrictCustomList = new ArrayList<>();
    ArrayList<CustomItems> mWbDistrictCustomList = new ArrayList<>();
    ArrayList<CustomItems> mDefaultDistrictCustomList = new ArrayList<>();
    ArrayList<CustomItems> mGenderCustomList = new ArrayList<>();
    ArrayList<CustomItems> mMaritalcustomList = new ArrayList<>();
    ArrayList<CustomItems> mEducationCustomList = new ArrayList<>();
    ArrayList<CustomItems> mOccupationCustomList = new ArrayList<>();
    ArrayList<CustomItems> mEducationStatusList = new ArrayList<>();
    public String mMaritalStatus, mEducation, mOccupation, mEducationStatus, mStateString, mDistrictString;
    private LinearLayout mMaleLinearLayout, mFemaleLinearLayout;
    private UserRegister mUserRegister = new UserRegister();
    private String mGender;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mDistrictSpinner = findViewById(R.id.district_spinner);
        mFullName = findViewById(R.id.full_name_edit_text);
        mFatherName = findViewById(R.id.father_name_edit_Text);
        mMotherName = findViewById(R.id.mother_name_edit_Text);
        mMobileNumber = findViewById(R.id.mobile_number_edit_text);
        mOccupationEditText = findViewById(R.id.occupation_edit_text);
        mFlatEditText = findViewById(R.id.flat_room_edit_text);
        mBuildingEditText = findViewById(R.id.building_edit_text);
        mAreaEditText = findViewById(R.id.area_edit_text);
        mPinCodeEditText = findViewById(R.id.pin_code_edit_text);
        mGenderSpinner = findViewById(R.id.gender_spinner);
        mDOBEditText = findViewById(R.id.dob_text_view);
        mRegisterButton = findViewById(R.id.register_button);
        mMaleLinearLayout = findViewById(R.id.male_linear_layout);
        mFemaleLinearLayout = findViewById(R.id.female_linear_layout);
        mEducationStatusSpinner = findViewById(R.id.education_status_spinner);
        getMariatalStatus();
        getEducation();
        getOccupationStatus();
        getState();
//        getUpDistrict(0);
//        getWbDistrict();
//        getDistrict();
        registerUser();
        getDob();
        getGender();
        getEducationStatus();
//        Log.w(TAG,"Get Gender: "+getGender());
    }

    public void getGender(){
        mGenderCustomList.add(new CustomItems("Select Gender"));
        mGenderCustomList.add(new CustomItems("Male"));
        mGenderCustomList.add(new CustomItems("Female"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mGenderCustomList);

        if (mGenderCustomList != null) {
            mGenderSpinner.setAdapter(customAdapter);
            mGenderSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getEducationStatus(){
        mEducationStatusList.add(new CustomItems("Education Status"));
        mEducationStatusList.add(new CustomItems("Pursue"));
        mEducationStatusList.add(new CustomItems("Completed"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mEducationStatusList);

        if (mEducationStatusList != null) {
            mEducationStatusSpinner.setAdapter(customAdapter);
            mEducationStatusSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getDob(){
        mDOBEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar lCal = Calendar.getInstance();
                int lYear = lCal.get(Calendar.YEAR);
                int lMonth = lCal.get(Calendar.MONTH);
                int lDay = lCal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog lDialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener,
                        lYear, lMonth, lDay);
                lDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                lDialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int aYear, int aMonth, int aDay) {
                Log.w("MainActivity", "DatePicker: "+aYear+ "/" +aMonth + "/" +aDay);
                aMonth = aMonth + 1;
                String lString = aYear + "/" + aMonth + "/" + aDay;
                mDOBEditText.setText(lString);
            }
        };

    }
    public void getMariatalStatus(){
        mMaritalSpinner = findViewById(R.id.marital_spinner);

        mMaritalcustomList.add(new CustomItems("Marital Status"));
        mMaritalcustomList.add(new CustomItems("Single"));
        mMaritalcustomList.add(new CustomItems("Married"));
        mMaritalcustomList.add(new CustomItems("Divorced"));
        mMaritalcustomList.add(new CustomItems("widow(er)"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mMaritalcustomList);

        if (mMaritalSpinner != null) {
            mMaritalSpinner.setAdapter(customAdapter);
            mMaritalSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getEducation(){
        mEducationSpinner = findViewById(R.id.eucation_spinner);

        mEducationCustomList.add(new CustomItems("Qualification"));
        mEducationCustomList.add(new CustomItems("SSC"));
        mEducationCustomList.add(new CustomItems("HSC"));
        mEducationCustomList.add(new CustomItems("Graduation"));
        mEducationCustomList.add(new CustomItems("Post Graduation"));

        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mEducationCustomList);

        if (mEducationSpinner != null) {
            mEducationSpinner.setAdapter(customAdapter);
            mEducationSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getOccupationStatus(){
        mOccupationSpinner = findViewById(R.id.occupation_spinner);
        mOccupationCustomList.add(new CustomItems("Occupation"));
        mOccupationCustomList.add(new CustomItems("Private job"));
        mOccupationCustomList.add(new CustomItems("Govt job"));
        mOccupationCustomList.add(new CustomItems("House Wife"));
        mOccupationCustomList.add(new CustomItems("Business"));
        mOccupationCustomList.add(new CustomItems("Retire"));
        mOccupationCustomList.add(new CustomItems("Leader"));
        mOccupationCustomList.add(new CustomItems("Student"));
        mOccupationCustomList.add(new CustomItems("Other"));

        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mOccupationCustomList);

        if (mOccupationSpinner != null) {
            mOccupationSpinner.setAdapter(customAdapter);
            mOccupationSpinner.setOnItemSelectedListener(this);
        }

    }

    public void getState(){
        mStateSpinner = findViewById(R.id.state_spinner);
        mStateCustomList.add(new CustomItems("Select State"));
        mStateCustomList.add(new CustomItems("Andhra Pradesh"));
        mStateCustomList.add(new CustomItems("Arunachal Pradesh"));
        mStateCustomList.add(new CustomItems("Assam"));
        mStateCustomList.add(new CustomItems("Bihar"));
        mStateCustomList.add(new CustomItems("Chhattisgarh"));
        mStateCustomList.add(new CustomItems("Goa"));
        mStateCustomList.add(new CustomItems("Gujarat"));
        mStateCustomList.add(new CustomItems("Haryana"));
        mStateCustomList.add(new CustomItems("Himachal Pradesh"));
        mStateCustomList.add(new CustomItems("Jammu and kashmir"));
        mStateCustomList.add(new CustomItems("Ladakh"));
        mStateCustomList.add(new CustomItems("Jharkhand"));
        mStateCustomList.add(new CustomItems("Karnataka"));
        mStateCustomList.add(new CustomItems("Kerala"));
        mStateCustomList.add(new CustomItems("Madhya Pradesh"));
        mStateCustomList.add(new CustomItems("Manipur"));
        mStateCustomList.add(new CustomItems("Meghalaya"));
        mStateCustomList.add(new CustomItems("Mizoram"));
        mStateCustomList.add(new CustomItems("Nagaland"));
        mStateCustomList.add(new CustomItems("Odisha"));
        mStateCustomList.add(new CustomItems("Punjab"));
        mStateCustomList.add(new CustomItems("Rajasthan"));
        mStateCustomList.add(new CustomItems("Sikkim"));
        mStateCustomList.add(new CustomItems("Tamil Nadu"));
        mStateCustomList.add(new CustomItems("Telangana"));
        mStateCustomList.add(new CustomItems("Uttarakhand"));
        mStateCustomList.add(new CustomItems("Uttar Pradesh"));
        mStateCustomList.add(new CustomItems("West Bengal"));


        // create Adapter for spinner
        CustomAdapter customAdapter = new CustomAdapter(this, mStateCustomList);

        if (mStateSpinner != null) {
            mStateSpinner.setAdapter(customAdapter);
            mStateSpinner.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int aPosition, long l) {

        switch (adapterView.getId()){
            case R.id.marital_spinner:
                mMaritalStatus = mMaritalcustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.eucation_spinner:
                mEducation = mEducationCustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.occupation_spinner:
                mOccupation = mOccupationCustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.gender_spinner:
                mGender = mGenderCustomList.get(aPosition).getSpinnerText();
                break;
            case R.id.education_status_spinner:
                mEducationStatus = mEducationStatusList.get(aPosition).getSpinnerText();
                break;
            case R.id.state_spinner:
                mStateString = mStateCustomList.get(aPosition).getSpinnerText();
            case R.id.district_spinner:
                Log.w(TAG,"SELECTED ITEM: "+mStateCustomList.get(aPosition).getSpinnerText());
                String sp1= String.valueOf(mStateCustomList.get(aPosition).getSpinnerText());
                Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
                switch(sp1){
                    case "Andhra Pradesh":
                        List<String> lAplist = new ArrayList<String>();
                        lAplist.add("Select District");
                        lAplist.add("Anantapur");
                        lAplist.add("Chittoor");
                        lAplist.add("East Godavari");
                        lAplist.add("Guntur");
                        lAplist.add("Kadapa");
                        lAplist.add("Krishna");
                        lAplist.add("Kurnool");
                        lAplist.add("Prakasam");
                        lAplist.add("Sri Potti Sriramulu Nellore");
                        lAplist.add("Srikakulam");
                        lAplist.add("Visakhapatnam");
                        lAplist.add("Vizianagaram");
                        lAplist.add("West Godavari");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lAplist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(dataAdapter);
                        mDistrictString = adapterView.getItemAtPosition(aPosition).toString();
                        Log.w(TAG,"District text: "+mDistrictString);
                        break;

                    case "Arunachal Pradesh":
                        List<String> lArunachalPradeshDistrictlist = new ArrayList<String>();
                        lArunachalPradeshDistrictlist.add("Select District");
                        lArunachalPradeshDistrictlist.add("Anjaw");
                        lArunachalPradeshDistrictlist.add("Changlang");
                        lArunachalPradeshDistrictlist.add("East Kameng");
                        lArunachalPradeshDistrictlist.add("East Siang");
                        lArunachalPradeshDistrictlist.add("Kamle");
                        lArunachalPradeshDistrictlist.add("Kra Daadi");
                        lArunachalPradeshDistrictlist.add("Kurung Kumey");
                        lArunachalPradeshDistrictlist.add("Lepa Rada");
                        lArunachalPradeshDistrictlist.add("Lohit");
                        lArunachalPradeshDistrictlist.add("Longding");
                        lArunachalPradeshDistrictlist.add("Lower Dibang Valley");
                        lArunachalPradeshDistrictlist.add("Lower Siang");
                        lArunachalPradeshDistrictlist.add("Lower Subansiri");
                        lArunachalPradeshDistrictlist.add("Namsai");
                        lArunachalPradeshDistrictlist.add("Pakke-Kessang");
                        lArunachalPradeshDistrictlist.add("Papum Pare");
                        lArunachalPradeshDistrictlist.add("Shi Yomi");
                        lArunachalPradeshDistrictlist.add("Siang");
                        lArunachalPradeshDistrictlist.add("Tawang");
                        lArunachalPradeshDistrictlist.add("Tirap");
                        lArunachalPradeshDistrictlist.add("Upper Dibang Valley");
                        lArunachalPradeshDistrictlist.add("Upper Siang");
                        lArunachalPradeshDistrictlist.add("Upper Subansiri");
                        lArunachalPradeshDistrictlist.add("West Kameng");
                        lArunachalPradeshDistrictlist.add("West Siang");
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lArunachalPradeshDistrictlist);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter2.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(dataAdapter2);
                        break;

                    case "Assam":
                        List<String> lAssamDistrictlist = new ArrayList<String>();
                        lAssamDistrictlist.add("Select District");
                        lAssamDistrictlist.add("Baksa");
                        lAssamDistrictlist.add("Barpeta");
                        lAssamDistrictlist.add("Bishwanath");
                        lAssamDistrictlist.add("Bongaigaon");
                        lAssamDistrictlist.add("Cachar");
                        lAssamDistrictlist.add("Charaideo");
                        lAssamDistrictlist.add("Chirang");
                        lAssamDistrictlist.add("Darrang");
                        lAssamDistrictlist.add("Dhemaji");
                        lAssamDistrictlist.add("Dhubri");
                        lAssamDistrictlist.add("Dibrugarh");
                        lAssamDistrictlist.add("Dima Hasao");
                        lAssamDistrictlist.add("Goalpara");
                        lAssamDistrictlist.add("Golaghat");
                        lAssamDistrictlist.add("Hailakandi");
                        lAssamDistrictlist.add("Hojai");
                        lAssamDistrictlist.add("Jorhat");
                        lAssamDistrictlist.add("Kamrup");
                        lAssamDistrictlist.add("Kamrup Metropolitan");
                        lAssamDistrictlist.add("Karbi Anglong");
                        lAssamDistrictlist.add("Karimganj");
                        lAssamDistrictlist.add("Kokrajhar");
                        lAssamDistrictlist.add("Lakhimpur");
                        lAssamDistrictlist.add("Majuli");
                        lAssamDistrictlist.add("Morigaon");
                        lAssamDistrictlist.add("Nagaon");
                        lAssamDistrictlist.add("Nalbari");
                        lAssamDistrictlist.add("Sivasagar");
                        lAssamDistrictlist.add("South Salmara");
                        lAssamDistrictlist.add("Sonitpur");
                        lAssamDistrictlist.add("Tinsukia");
                        lAssamDistrictlist.add("Udalguri");
                        lAssamDistrictlist.add("West Karbi Anglong");
                        ArrayAdapter<String> lAssamAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lAssamDistrictlist);
                        lAssamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lAssamAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lAssamAdapter);
                        break;
                    case "Bihar":
                        List<String> lBiharDistrictlist = new ArrayList<String>();
                        lBiharDistrictlist.add("Select District");
                        lBiharDistrictlist.add("Araria");
                        lBiharDistrictlist.add("Arwal");
                        lBiharDistrictlist.add("Aurangabad");
                        lBiharDistrictlist.add("Banka");
                        lBiharDistrictlist.add("Begusarai");
                        lBiharDistrictlist.add("Bhagalpur");
                        lBiharDistrictlist.add("Bhojpur");
                        lBiharDistrictlist.add("Buxar");
                        lBiharDistrictlist.add("Darbhanga");
                        lBiharDistrictlist.add("East Champaran");
                        lBiharDistrictlist.add("Gaya");
                        lBiharDistrictlist.add("Gopalganj");
                        lBiharDistrictlist.add("Jamui");
                        lBiharDistrictlist.add("Jehanabad");
                        lBiharDistrictlist.add("Kaimur");
                        lBiharDistrictlist.add("Katihar");
                        lBiharDistrictlist.add("Khagaria");
                        lBiharDistrictlist.add("Kishanganj");
                        lBiharDistrictlist.add("Lakhisarai");
                        lBiharDistrictlist.add("Madhepura");
                        lBiharDistrictlist.add("Madhubani");
                        lBiharDistrictlist.add("Munger");
                        lBiharDistrictlist.add("Muzaffarpur");
                        lBiharDistrictlist.add("Nalanda");
                        lBiharDistrictlist.add("Nawada");
                        lBiharDistrictlist.add("Patna");
                        lBiharDistrictlist.add("Purnia");
                        lBiharDistrictlist.add("Rohtas");
                        lBiharDistrictlist.add("Saharsa");
                        lBiharDistrictlist.add("Samastipur");
                        lBiharDistrictlist.add("Saran");
                        lBiharDistrictlist.add("Sheikhpura");
                        lBiharDistrictlist.add("Sheohar");
                        lBiharDistrictlist.add("Sitamarhi");
                        lBiharDistrictlist.add("Siwan");
                        lBiharDistrictlist.add("Supaul");
                        lBiharDistrictlist.add("Vaishali");
                        lBiharDistrictlist.add("West Champaran");
                        ArrayAdapter<String> lBiharAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lBiharDistrictlist);
                        lBiharAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lBiharAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lBiharAdapter);
                        break;
                    case "Chhattisgarh":
                        List<String> lChhattisgarhDistrictlist = new ArrayList<String>();
                        lChhattisgarhDistrictlist.add("Select District");
                        lChhattisgarhDistrictlist.add("Balod");
                        lChhattisgarhDistrictlist.add("Baloda Bazar");
                        lChhattisgarhDistrictlist.add("Balrampur");
                        lChhattisgarhDistrictlist.add("Bastar");
                        lChhattisgarhDistrictlist.add("Bemetara");
                        lChhattisgarhDistrictlist.add("Bijapur");
                        lChhattisgarhDistrictlist.add("Bilaspur");
                        lChhattisgarhDistrictlist.add("Dantewada");
                        lChhattisgarhDistrictlist.add("Dhamtari");
                        lChhattisgarhDistrictlist.add("Durg");
                        lChhattisgarhDistrictlist.add("Gariaband");
                        lChhattisgarhDistrictlist.add("Gaurela-Pendra-Marwahi\t");
                        lChhattisgarhDistrictlist.add("Janjgir-Champa");
                        lChhattisgarhDistrictlist.add("Jashpur");
                        lChhattisgarhDistrictlist.add("Kabirdham");
                        lChhattisgarhDistrictlist.add("Kanker");
                        lChhattisgarhDistrictlist.add("Kondagaon");
                        lChhattisgarhDistrictlist.add("Korba");
                        lChhattisgarhDistrictlist.add("Koriya");
                        lChhattisgarhDistrictlist.add("Mahasamund");
                        lChhattisgarhDistrictlist.add("Mungeli");
                        lChhattisgarhDistrictlist.add("Narayanpur");
                        lChhattisgarhDistrictlist.add("Raigarh");
                        lChhattisgarhDistrictlist.add("Raipur");
                        lChhattisgarhDistrictlist.add("Rajnandgaon");
                        lChhattisgarhDistrictlist.add("Sukma");
                        lChhattisgarhDistrictlist.add("Surajpur");
                        lChhattisgarhDistrictlist.add("Surguja");
                        ArrayAdapter<String> lChhattisgarhAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lChhattisgarhDistrictlist);
                        lChhattisgarhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lChhattisgarhAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lChhattisgarhAdapter);
                        break;
                    case "Goa":
                        List<String> lGoaDistrictlist = new ArrayList<String>();
                        lGoaDistrictlist.add("Select District");
                        lGoaDistrictlist.add("North Goa");
                        lGoaDistrictlist.add("South Goa");
                        ArrayAdapter<String> lGoaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lGoaDistrictlist);
                        lGoaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lGoaAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lGoaAdapter);
                        mDistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                mDistrictString = mDistrictSpinner.getSelectedItem().toString();
                                Log.w(TAG,"District text: "+mDistrictString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case "Gujarat":
                        List<String> lGujaratDistrictlist = new ArrayList<String>();
                        lGujaratDistrictlist.add("Select District");
                        lGujaratDistrictlist.add("Vadodara");
                        lGujaratDistrictlist.add("Rajkot");
                        lGujaratDistrictlist.add("Valsad");
                        lGujaratDistrictlist.add("Surat");
                        lGujaratDistrictlist.add("Patan");
                        lGujaratDistrictlist.add("Bharuch");
                        lGujaratDistrictlist.add("The Dangs");
                        lGujaratDistrictlist.add("Bhavnagar");
                        lGujaratDistrictlist.add("Ahmadabad");
                        lGujaratDistrictlist.add("Dahod");
                        lGujaratDistrictlist.add("Kheda");
                        lGujaratDistrictlist.add("Sabar Kantha");
                        lGujaratDistrictlist.add("Surendranagar");
                        lGujaratDistrictlist.add("Gandhinagar");
                        lGujaratDistrictlist.add("Banas Kantha");
                        lGujaratDistrictlist.add("Kachchh");
                        lGujaratDistrictlist.add("Junagadh");
                        lGujaratDistrictlist.add("Jamnagar");
                        lGujaratDistrictlist.add("Mahesana");
                        lGujaratDistrictlist.add("Rann of Kachchh");
                        lGujaratDistrictlist.add("Navsari");
                        lGujaratDistrictlist.add("Rajpipla");
                        lGujaratDistrictlist.add("Amreli");
                        lGujaratDistrictlist.add("Panch Mahals");
                        lGujaratDistrictlist.add("Anand");
                        ArrayAdapter<String> lGujaratAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lGujaratDistrictlist);
                        lGujaratAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lGujaratAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lGujaratAdapter);
                       break;
                    case "Haryana":
                        List<String> lHaryanaDistrictlist = new ArrayList<String>();
                        lHaryanaDistrictlist.add("Select District");
                        lHaryanaDistrictlist.add("Ambala");
                        lHaryanaDistrictlist.add("Bhiwani");
                        lHaryanaDistrictlist.add("Charkhi Dadri");
                        lHaryanaDistrictlist.add("Faridabad");
                        lHaryanaDistrictlist.add("Fatehabad");
                        lHaryanaDistrictlist.add("Gurugram");
                        lHaryanaDistrictlist.add("Hissar");
                        lHaryanaDistrictlist.add("Jhajjar");
                        lHaryanaDistrictlist.add("Jind");
                        lHaryanaDistrictlist.add("Kaithal");
                        lHaryanaDistrictlist.add("Karnal");
                        lHaryanaDistrictlist.add("Kurukshetra");
                        lHaryanaDistrictlist.add("Mahendragarh");
                        lHaryanaDistrictlist.add("Nuh");
                        lHaryanaDistrictlist.add("Palwal");
                        lHaryanaDistrictlist.add("Panchkula");
                        lHaryanaDistrictlist.add("Panipat");
                        lHaryanaDistrictlist.add("Rewari");
                        lHaryanaDistrictlist.add("Rohtak");
                        lHaryanaDistrictlist.add("Sirsa");
                        lHaryanaDistrictlist.add("Sonipat");
                        lHaryanaDistrictlist.add("Yamuna Nagar");
                        ArrayAdapter<String> lHaryanaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lHaryanaDistrictlist);
                        lHaryanaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lHaryanaAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lHaryanaAdapter);
                        break;
                    case "Himachal Pradesh":
                        List<String> lHpDistrictlist = new ArrayList<String>();
                        lHpDistrictlist.add("Select District");
                        lHpDistrictlist.add("Bilaspur");
                        lHpDistrictlist.add("Chamba");
                        lHpDistrictlist.add("Hamirpur");
                        lHpDistrictlist.add("Kangra");
                        lHpDistrictlist.add("Kinnaur");
                        lHpDistrictlist.add("Kullu");
                        lHpDistrictlist.add("Lahaul and Spiti");
                        lHpDistrictlist.add("Mandi");
                        lHpDistrictlist.add("Shimla");
                        lHpDistrictlist.add("Sirmaur");
                        lHpDistrictlist.add("Solan");
                        lHpDistrictlist.add("Una");
                        ArrayAdapter<String> lHPAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lHpDistrictlist);
                        lHPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lHPAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lHPAdapter);
                        break;
                    case "Jammu and kashmir":
                        List<String> lJKDistrictlist = new ArrayList<String>();
                        lJKDistrictlist.add("Select District");
                        lJKDistrictlist.add("Anantnag");
                        lJKDistrictlist.add("Bandipora");
                        lJKDistrictlist.add("Baramulla");
                        lJKDistrictlist.add("Badgam");
                        lJKDistrictlist.add("Doda");
                        lJKDistrictlist.add("Ganderbal");
                        lJKDistrictlist.add("Jammu");
                        lJKDistrictlist.add("Kathua");
                        lJKDistrictlist.add("Kishtwar");
                        lJKDistrictlist.add("Kulgam");
                        lJKDistrictlist.add("Kupwara");
                        lJKDistrictlist.add("Poonch");
                        lJKDistrictlist.add("Pulwama");
                        lJKDistrictlist.add("Rajouri");
                        lJKDistrictlist.add("Ramban");
                        lJKDistrictlist.add("Reasi");
                        lJKDistrictlist.add("Samba");
                        lJKDistrictlist.add("Shopian");
                        lJKDistrictlist.add("Srinagar");
                        lJKDistrictlist.add("Udhampur");
                        ArrayAdapter<String> lJKAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lJKDistrictlist);
                        lJKAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lJKAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lJKAdapter);
                        break;
                    case "Ladakh":
                        List<String> lLadakhistrictlist = new ArrayList<String>();
                        lLadakhistrictlist.add("Select District");
                        lLadakhistrictlist.add("Kargil");
                        lLadakhistrictlist.add("Leh");
                        ArrayAdapter<String> lLadakhAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lLadakhistrictlist);
                        lLadakhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lLadakhAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lLadakhAdapter);
                        break;
                    case "Jharkhand":
                        List<String> lJDistrictlist = new ArrayList<String>();
                        lJDistrictlist.add("Select District");
                        lJDistrictlist.add("Bokaro");
                        lJDistrictlist.add("Chatra");
                        lJDistrictlist.add("Deoghar");
                        lJDistrictlist.add("Dhanbad");
                        lJDistrictlist.add("Dumka");
                        lJDistrictlist.add("East Singhbhum");
                        lJDistrictlist.add("Garhwa");
                        lJDistrictlist.add("Giridih");
                        lJDistrictlist.add("Godda");
                        lJDistrictlist.add("Gumla");
                        lJDistrictlist.add("Hazaribag");
                        lJDistrictlist.add("Jamtara");
                        lJDistrictlist.add("Khunti");
                        lJDistrictlist.add("Koderma");
                        lJDistrictlist.add("Latehar");
                        lJDistrictlist.add("Lohardaga");
                        lJDistrictlist.add("Pakur");
                        lJDistrictlist.add("Palamu");
                        lJDistrictlist.add("Ramgarh");
                        lJDistrictlist.add("Ranchi");
                        lJDistrictlist.add("Sahibganj");
                        lJDistrictlist.add("Seraikela Kharsawan");
                        lJDistrictlist.add("Simdega");
                        lJDistrictlist.add("West Singhbhum");
                        ArrayAdapter<String> lJAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lJDistrictlist);
                        lJAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lJAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lJAdapter);
                        break;
                    case "Karnataka":
                        List<String> lKDistrictlist = new ArrayList<String>();
                        lKDistrictlist.add("Select District");
                        lKDistrictlist.add("Bagalkot");
                        lKDistrictlist.add("Ballari");
                        lKDistrictlist.add("Belagavi");
                        lKDistrictlist.add("Bengaluru Rural");
                        lKDistrictlist.add("Bengaluru Urban");
                        lKDistrictlist.add("Bidar");
                        lKDistrictlist.add("Chamarajnagar");
                        lKDistrictlist.add("Chikkaballapur");
                        lKDistrictlist.add("Chikkamagaluru");
                        lKDistrictlist.add("Chitradurga");
                        lKDistrictlist.add("Dakshina Kannada");
                        lKDistrictlist.add("Davanagere");
                        lKDistrictlist.add("Dharwad");
                        lKDistrictlist.add("Gadag");
                        lKDistrictlist.add("Hassan");
                        lKDistrictlist.add("Haveri");
                        lKDistrictlist.add("Kalaburagi");
                        lKDistrictlist.add("Kodagu");
                        lKDistrictlist.add("Kolar");
                        lKDistrictlist.add("Koppal");
                        lKDistrictlist.add("Mandya");
                        lKDistrictlist.add("Mysuru");
                        lKDistrictlist.add("Raichur");
                        lKDistrictlist.add("Ramanagara");
                        lKDistrictlist.add("Shivamogga");
                        lKDistrictlist.add("Tumakuru");
                        lKDistrictlist.add("Udupi");
                        lKDistrictlist.add("Uttara Kannada");
                        lKDistrictlist.add("Vijayapura");
                        lKDistrictlist.add("Yadgir");
                        ArrayAdapter<String> lKAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lKDistrictlist);
                        lKAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lKAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lKAdapter);
                        break;
                    case "Kerala":
                        List<String> lKeralaDistrictlist = new ArrayList<String>();
                        lKeralaDistrictlist.add("Select District");
                        lKeralaDistrictlist.add("Alappuzha");
                        lKeralaDistrictlist.add("Ernakulam");
                        lKeralaDistrictlist.add("Idukki");
                        lKeralaDistrictlist.add("Kannur");
                        lKeralaDistrictlist.add("Kasaragod");
                        lKeralaDistrictlist.add("Kollam");
                        lKeralaDistrictlist.add("Kottayam");
                        lKeralaDistrictlist.add("Kozhikode");
                        lKeralaDistrictlist.add("Malappuram");
                        lKeralaDistrictlist.add("Palakkad");
                        lKeralaDistrictlist.add("Pathanamthitta");
                        lKeralaDistrictlist.add("Thrissur");
                        lKeralaDistrictlist.add("Thiruvananthapuram");
                        lKeralaDistrictlist.add("Wayanad");
                        ArrayAdapter<String> lKeralaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lKeralaDistrictlist);
                        lKeralaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lKeralaAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lKeralaAdapter);
                        break;
                    case "Madhya Pradesh":
                        List<String> lMpDistrictlist = new ArrayList<String>();
                        lMpDistrictlist.add("Select District");
                        lMpDistrictlist.add("Agar Malwa");
                        lMpDistrictlist.add("Alirajpur");
                        lMpDistrictlist.add("Anuppur");
                        lMpDistrictlist.add("Ashok Nagar");
                        lMpDistrictlist.add("Balaghat");
                        lMpDistrictlist.add("Barwani");
                        lMpDistrictlist.add("Betul");
                        lMpDistrictlist.add("Bhind");
                        lMpDistrictlist.add("Bhopal");
                        lMpDistrictlist.add("Burhanpur");
                        lMpDistrictlist.add("Chhatarpur");
                        lMpDistrictlist.add("Chhindwara");
                        lMpDistrictlist.add("Damoh");
                        lMpDistrictlist.add("Datia");
                        lMpDistrictlist.add("Dewas");
                        lMpDistrictlist.add("Dhar");
                        lMpDistrictlist.add("Dindori");
                        lMpDistrictlist.add("Guna");
                        lMpDistrictlist.add("Gwalior");
                        lMpDistrictlist.add("Harda");
                        lMpDistrictlist.add("Hoshangabad");
                        lMpDistrictlist.add("Indore");
                        lMpDistrictlist.add("Jabalpur");
                        lMpDistrictlist.add("Jhabua");
                        lMpDistrictlist.add("Katni");
                        lMpDistrictlist.add("Khandwa");
                        lMpDistrictlist.add("Khargone");
                        lMpDistrictlist.add("Mandla");
                        lMpDistrictlist.add("Mandsaur");
                        lMpDistrictlist.add("Morena");
                        lMpDistrictlist.add("Narsinghpur");
                        lMpDistrictlist.add("Neemuch");
                        lMpDistrictlist.add("Niwari");
                        lMpDistrictlist.add("Panna");
                        lMpDistrictlist.add("Raisen");
                        lMpDistrictlist.add("Rajgarh");
                        lMpDistrictlist.add("Ratlam");
                        lMpDistrictlist.add("Rewa");
                        lMpDistrictlist.add("Sagar");
                        lMpDistrictlist.add("Satna");
                        lMpDistrictlist.add("Sehore");
                        lMpDistrictlist.add("Seoni");
                        lMpDistrictlist.add("Shahdol");
                        lMpDistrictlist.add("Shajapur");
                        lMpDistrictlist.add("Sheopur");
                        lMpDistrictlist.add("Shivpuri");
                        lMpDistrictlist.add("Sidhi");
                        lMpDistrictlist.add("Singrauli");
                        lMpDistrictlist.add("Tikamgarh");
                        lMpDistrictlist.add("Ujjain");
                        lMpDistrictlist.add("Umaria");
                        lMpDistrictlist.add("Vidisha");
                        ArrayAdapter<String> lMpAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lMpDistrictlist);
                        lMpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lMpAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lMpAdapter);
                        break;
                    case "Manipur":
                        List<String> lManipurDistrictlist = new ArrayList<String>();
                        lManipurDistrictlist.add("Select District");
                        lManipurDistrictlist.add("Bishnupur");
                        lManipurDistrictlist.add("Chandel");
                        lManipurDistrictlist.add("Churachandpur");
                        lManipurDistrictlist.add("Imphal East");
                        lManipurDistrictlist.add("Imphal West");
                        lManipurDistrictlist.add("Jiribam");
                        lManipurDistrictlist.add("Kakching");
                        lManipurDistrictlist.add("Kamjong");
                        lManipurDistrictlist.add("Kangpokpi");
                        lManipurDistrictlist.add("Noney");
                        lManipurDistrictlist.add("Pherzawl");
                        lManipurDistrictlist.add("Senapati");
                        lManipurDistrictlist.add("Tamenglong");
                        lManipurDistrictlist.add("Tengnoupal");
                        lManipurDistrictlist.add("Thoubal");
                        lManipurDistrictlist.add("Ukhrul");
                        ArrayAdapter<String> lManipurAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lManipurDistrictlist);
                        lManipurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lManipurAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lManipurAdapter);
                        break;
                    case "Meghalaya":
                        List<String> lMeghalayaDistrictlist = new ArrayList<String>();
                        lMeghalayaDistrictlist.add("Select District");
                        lMeghalayaDistrictlist.add("East Garo Hills");
                        lMeghalayaDistrictlist.add("East Khasi Hills");
                        lMeghalayaDistrictlist.add("East Jaintia Hills");
                        lMeghalayaDistrictlist.add("North Garo Hills");
                        lMeghalayaDistrictlist.add("Ri Bhoi");
                        lMeghalayaDistrictlist.add("South Garo Hills");
                        lMeghalayaDistrictlist.add("South West Garo Hills");
                        lMeghalayaDistrictlist.add("South West Khasi Hills");
                        lMeghalayaDistrictlist.add("West Jaintia Hills");
                        lMeghalayaDistrictlist.add("West Garo Hills");
                        lMeghalayaDistrictlist.add("West Khasi Hills");
                        ArrayAdapter<String> lMeghalayaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lMeghalayaDistrictlist);
                        lMeghalayaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lMeghalayaAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lMeghalayaAdapter);
                        break;
                    case "Mizoram":
                        List<String> lMZDistrictlist = new ArrayList<String>();
                        lMZDistrictlist.add("Select District");
                        lMZDistrictlist.add("Aizawl");
                        lMZDistrictlist.add("Champhai");
                        lMZDistrictlist.add("Kolasib");
                        lMZDistrictlist.add("Lawngtlai");
                        lMZDistrictlist.add("Lunglei");
                        lMZDistrictlist.add("Mamit");
                        lMZDistrictlist.add("Saiha");
                        lMZDistrictlist.add("Serchhip");
                        ArrayAdapter<String> lMZAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lMZDistrictlist);
                        lMZAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lMZAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lMZAdapter);
                        break;
                    case "Nagaland":
                        List<String> lNLDistrictlist = new ArrayList<String>();
                        lNLDistrictlist.add("Select District");
                        lNLDistrictlist.add("Dimapur");
                        lNLDistrictlist.add("Kiphire");
                        lNLDistrictlist.add("Kohima");
                        lNLDistrictlist.add("Longleng");
                        lNLDistrictlist.add("Mokokchung");
                        lNLDistrictlist.add("Mon");
                        lNLDistrictlist.add("Noklak");
                        lNLDistrictlist.add("Peren");
                        lNLDistrictlist.add("Phek");
                        lNLDistrictlist.add("Tuensang");
                        lNLDistrictlist.add("Wokha");
                        lNLDistrictlist.add("Zunheboto");
                        ArrayAdapter<String> lNlAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lNLDistrictlist);
                        lNlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lNlAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lNlAdapter);
                        break;
                    case "Odisha":
                        List<String> lODDistrictlist = new ArrayList<String>();
                        lODDistrictlist.add("Select District");
                        lODDistrictlist.add("Angul");
                        lODDistrictlist.add("Boudh");
                        lODDistrictlist.add("Bhadrak");
                        lODDistrictlist.add("Balangir");
                        lODDistrictlist.add("Balasore");
                        lODDistrictlist.add("Cuttack");
                        lODDistrictlist.add("Debagarh");
                        lODDistrictlist.add("Dhenkanal");
                        lODDistrictlist.add("Ganjam");
                        lODDistrictlist.add("Gajapati");
                        lODDistrictlist.add("Jharsuguda");
                        lODDistrictlist.add("Jajpur");
                        lODDistrictlist.add("Jagatsinghpur");
                        lODDistrictlist.add("Khordha");
                        lODDistrictlist.add("Kendujhar");
                        lODDistrictlist.add("Kalahandi");
                        lODDistrictlist.add("Kandhamal");
                        lODDistrictlist.add("Koraput");
                        lODDistrictlist.add("Kendrapara");
                        lODDistrictlist.add("Malkangiri");
                        lODDistrictlist.add("Mayurbhanj");
                        lODDistrictlist.add("Nabarangpur");
                        lODDistrictlist.add("Nuapada");
                        lODDistrictlist.add("Nayagarh");
                        lODDistrictlist.add("Puri");
                        lODDistrictlist.add("Rayagada");
                        lODDistrictlist.add("Sambalpur");
                        lODDistrictlist.add("Subarnapur");
                        lODDistrictlist.add("Sundargarh");
                        ArrayAdapter<String> lODAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lODDistrictlist);
                        lODAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lODAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lODAdapter);
                        break;
                    case "Punjab":
                        List<String> lPBDistrictlist = new ArrayList<String>();
                        lPBDistrictlist.add("Select District");
                        lPBDistrictlist.add("Amritsar");
                        lPBDistrictlist.add("Barnala");
                        lPBDistrictlist.add("Bathinda");
                        lPBDistrictlist.add("Firozpur");
                        lPBDistrictlist.add("Faridkot");
                        lPBDistrictlist.add("Fatehgarh Sahib");
                        lPBDistrictlist.add("Fazilka");
                        lPBDistrictlist.add("Gurdaspur");
                        lPBDistrictlist.add("Hoshiarpur");
                        lPBDistrictlist.add("Jalandhar");
                        lPBDistrictlist.add("Kapurthala");
                        lPBDistrictlist.add("Ludhiana");
                        lPBDistrictlist.add("Mansa");
                        lPBDistrictlist.add("Moga");
                        lPBDistrictlist.add("Sri Muktsar Sahib");
                        lPBDistrictlist.add("Pathankot");
                        lPBDistrictlist.add("Patiala");
                        lPBDistrictlist.add("Rupnagar");
                        lPBDistrictlist.add("Sahibzada Ajit Singh Nagar");
                        lPBDistrictlist.add("Sangrur");
                        lPBDistrictlist.add("Shahid Bhagat Singh Nagar");
                        lPBDistrictlist.add("Tarn Taran");
                        ArrayAdapter<String> lPBAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lPBDistrictlist);
                        lPBAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lPBAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lPBAdapter);
                        break;
                    case "Rajasthan":
                        List<String> lRJDistrictlist = new ArrayList<String>();
                        lRJDistrictlist.add("Select District");
                        lRJDistrictlist.add("Ajmer");
                        lRJDistrictlist.add("Alwar");
                        lRJDistrictlist.add("Bikaner");
                        lRJDistrictlist.add("Barmer");
                        lRJDistrictlist.add("Banswara");
                        lRJDistrictlist.add("Bharatpur");
                        lRJDistrictlist.add("Baran");
                        lRJDistrictlist.add("Bundi");
                        lRJDistrictlist.add("Bhilwara");
                        lRJDistrictlist.add("Churu");
                        lRJDistrictlist.add("Chittorgarh");
                        lRJDistrictlist.add("Dausa");
                        lRJDistrictlist.add("Dholpur");
                        lRJDistrictlist.add("Dungarpur");
                        lRJDistrictlist.add("Ganganagar");
                        lRJDistrictlist.add("Hanumangarh");
                        lRJDistrictlist.add("Jhunjhunu");
                        lRJDistrictlist.add("Jalore");
                        lRJDistrictlist.add("Jodhpur");
                        lRJDistrictlist.add("Jaipur");
                        lRJDistrictlist.add("Jaisalmer");
                        lRJDistrictlist.add("Jhalawar");
                        lRJDistrictlist.add("Karauli");
                        lRJDistrictlist.add("Kota");
                        lRJDistrictlist.add("Nagaur");
                        lRJDistrictlist.add("Pali");
                        lRJDistrictlist.add("Pratapgarh");
                        lRJDistrictlist.add("Rajsamand");
                        lRJDistrictlist.add("Sikar");
                        lRJDistrictlist.add("Sawai Madhopur");
                        lRJDistrictlist.add("Sirohi");
                        lRJDistrictlist.add("Tonk");
                        lRJDistrictlist.add("Udaipur");
                        ArrayAdapter<String> lRJAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lRJDistrictlist);
                        lRJAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lRJAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lRJAdapter);
                        break;
                    case "Sikkim":
                        List<String> lSikkimDistrictlist = new ArrayList<String>();
                        lSikkimDistrictlist.add("Select District");
                        lSikkimDistrictlist.add("East Sikkim");
                        lSikkimDistrictlist.add("North Sikkim");
                        lSikkimDistrictlist.add("South Sikkim");
                        lSikkimDistrictlist.add("West Sikkim");
                        ArrayAdapter<String> SikkimAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lSikkimDistrictlist);
                        SikkimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        SikkimAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(SikkimAdapter);
                        break;
                    case "Tamil Nadu":
                        List<String> lTLDistrictlist = new ArrayList<String>();
                        lTLDistrictlist.add("Select District");
                        lTLDistrictlist.add("Ariyalur");
                        lTLDistrictlist.add("Chengalpattu");
                        lTLDistrictlist.add("Chennai");
                        lTLDistrictlist.add("Coimbatore");
                        lTLDistrictlist.add("Cuddalore");
                        lTLDistrictlist.add("Dharmapuri");
                        lTLDistrictlist.add("Dindigul");
                        lTLDistrictlist.add("Erode");
                        lTLDistrictlist.add("Kallakurichi");
                        lTLDistrictlist.add("Kanchipuram");
                        lTLDistrictlist.add("Kanyakumari");
                        lTLDistrictlist.add("Karur");
                        lTLDistrictlist.add("Krishnagiri");
                        lTLDistrictlist.add("Madurai");
                        lTLDistrictlist.add("Nagapattinam");
                        lTLDistrictlist.add("Nilgiris");
                        lTLDistrictlist.add("Namakkal");
                        lTLDistrictlist.add("Perambalur");
                        lTLDistrictlist.add("Pudukkottai");
                        lTLDistrictlist.add("Ramanathapuram");
                        lTLDistrictlist.add("Ranipet");
                        lTLDistrictlist.add("Salem");
                        lTLDistrictlist.add("Sivaganga");
                        lTLDistrictlist.add("Tenkasi");
                        lTLDistrictlist.add("Tirupur");
                        lTLDistrictlist.add("Tiruchirappalli");
                        lTLDistrictlist.add("Theni");
                        lTLDistrictlist.add("Tirunelveli");
                        lTLDistrictlist.add("Thanjavur");
                        lTLDistrictlist.add("Thoothukudi");
                        lTLDistrictlist.add("Tirupattur");
                        lTLDistrictlist.add("Tiruvallur");
                        lTLDistrictlist.add("Tiruvarur");
                        lTLDistrictlist.add("Tiruvannamalai");
                        lTLDistrictlist.add("Vellore");
                        lTLDistrictlist.add("Viluppuram");
                        lTLDistrictlist.add("Virudhunagar");
                        ArrayAdapter<String> lTNAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lTLDistrictlist);
                        lTNAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lTNAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lTNAdapter);
                        break;
                    case "Telangana":
                        List<String> lTelanganaDistrictlist = new ArrayList<String>();
                        lTelanganaDistrictlist.add("Select District");
                        lTelanganaDistrictlist.add("Adilabad");
                        lTelanganaDistrictlist.add("Komaram Bheem");
                        lTelanganaDistrictlist.add("Bhadradri Kothagudem");
                        lTelanganaDistrictlist.add("Hyderabad");
                        lTelanganaDistrictlist.add("Jagtial");
                        lTelanganaDistrictlist.add("Jangaon");
                        lTelanganaDistrictlist.add("Jayashankar Bhupalpally");
                        lTelanganaDistrictlist.add("Jogulamba Gadwal");
                        lTelanganaDistrictlist.add("Kamareddy");
                        lTelanganaDistrictlist.add("Karimnagar");
                        lTelanganaDistrictlist.add("Khammam");
                        lTelanganaDistrictlist.add("Mahabubabad");
                        lTelanganaDistrictlist.add("Mahbubnagar");
                        lTelanganaDistrictlist.add("Mancherial");
                        lTelanganaDistrictlist.add("Medak");
                        lTelanganaDistrictlist.add("Medchal-Malkajgiri");
                        lTelanganaDistrictlist.add("Mulugu");
                        lTelanganaDistrictlist.add("Nalgonda");
                        lTelanganaDistrictlist.add("Narayanpet");
                        lTelanganaDistrictlist.add("Nagarkurnool");
                        lTelanganaDistrictlist.add("Nirmal");
                        lTelanganaDistrictlist.add("Nizamabad");
                        lTelanganaDistrictlist.add("Peddapalli");
                        lTelanganaDistrictlist.add("Rajanna Sircilla");
                        lTelanganaDistrictlist.add("Ranga Reddy");
                        lTelanganaDistrictlist.add("Sangareddy");
                        lTelanganaDistrictlist.add("Siddipet");
                        lTelanganaDistrictlist.add("Suryapet");
                        lTelanganaDistrictlist.add("Vikarabad");
                        lTelanganaDistrictlist.add("Wanaparthy");
                        lTelanganaDistrictlist.add("Warangal Urban");
                        lTelanganaDistrictlist.add("Warangal Rural");
                        lTelanganaDistrictlist.add("Yadadri Bhuvanagiri");
                        ArrayAdapter<String> lTelanganaAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lTelanganaDistrictlist);
                        lTelanganaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lTelanganaAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lTelanganaAdapter);
                        break;
                    case "Uttarakhand":
                        List<String> lUKDistrictlist = new ArrayList<String>();
                        lUKDistrictlist.add("Select District");
                        lUKDistrictlist.add("Almora");
                        lUKDistrictlist.add("Bageshwar");
                        lUKDistrictlist.add("Chamoli");
                        lUKDistrictlist.add("Champawat");
                        lUKDistrictlist.add("Dehradun");
                        lUKDistrictlist.add("Haridwar");
                        lUKDistrictlist.add("Nainital");
                        lUKDistrictlist.add("Pauri Garhwal");
                        lUKDistrictlist.add("Pithoragarh");
                        lUKDistrictlist.add("Rudraprayag");
                        lUKDistrictlist.add("Tehri Garhwal");
                        lUKDistrictlist.add("Udham Singh Nagar");
                        lUKDistrictlist.add("Uttarkashi");
                        ArrayAdapter<String> lUKAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lUKDistrictlist);
                        lUKAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lUKAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lUKAdapter);
                        break;
                    case "Uttar Pradesh":
                        List<String> lUPDistrictlist = new ArrayList<String>();
                        lUPDistrictlist.add("Select District");
                        lUPDistrictlist.add("Agra");
                        lUPDistrictlist.add("Aligarh");
                        lUPDistrictlist.add("Allahabad");
                        lUPDistrictlist.add("Ambedkar Nagar");
                        lUPDistrictlist.add("Amethi");
                        lUPDistrictlist.add("Amroha");
                        lUPDistrictlist.add("Auraiya");
                        lUPDistrictlist.add("Azamgarh");
                        lUPDistrictlist.add("Bagpat");
                        lUPDistrictlist.add("Bahraich");
                        lUPDistrictlist.add("Ballia");
                        lUPDistrictlist.add("Balrampur");
                        lUPDistrictlist.add("Banda");
                        lUPDistrictlist.add("Barabanki");
                        lUPDistrictlist.add("Bareilly");
                        lUPDistrictlist.add("Basti");
                        lUPDistrictlist.add("Bhadohi");
                        lUPDistrictlist.add("Bijnor");
                        lUPDistrictlist.add("Budaun");
                        lUPDistrictlist.add("Bulandshahr");
                        lUPDistrictlist.add("Chandauli");
                        lUPDistrictlist.add("Chitrakoot");
                        lUPDistrictlist.add("Deoria");
                        lUPDistrictlist.add("Etah");
                        lUPDistrictlist.add("Etawah");
                        lUPDistrictlist.add("Faizabad");
                        lUPDistrictlist.add("Farrukhabad");
                        lUPDistrictlist.add("Fatehpur");
                        lUPDistrictlist.add("Firozabad");
                        lUPDistrictlist.add("Gautam Buddh Nagar");
                        lUPDistrictlist.add("Ghaziabad");
                        lUPDistrictlist.add("Ghazipur");
                        lUPDistrictlist.add("Gonda");
                        lUPDistrictlist.add("Gorakhpur");
                        lUPDistrictlist.add("Hamirpur");
                        lUPDistrictlist.add("Hapur");
                        lUPDistrictlist.add("Hardoi");
                        lUPDistrictlist.add("Hathras");
                        lUPDistrictlist.add("Jalaun");
                        lUPDistrictlist.add("Jaunpur");
                        lUPDistrictlist.add("Jhansi");
                        lUPDistrictlist.add("Kannauj");
                        lUPDistrictlist.add("Kanpur Dehat");
                        lUPDistrictlist.add("Kanpur Nagar");
                        lUPDistrictlist.add("Kasganj");
                        lUPDistrictlist.add("Kaushambi");
                        lUPDistrictlist.add("Kushinagar");
                        lUPDistrictlist.add("Lakhimpur Kheri");
                        lUPDistrictlist.add("Lalitpur");
                        lUPDistrictlist.add("Lucknow");
                        lUPDistrictlist.add("Maharajganj");
                        lUPDistrictlist.add("Mahoba");
                        lUPDistrictlist.add("Mainpuri");
                        lUPDistrictlist.add("Mathura");
                        lUPDistrictlist.add("Mau");
                        lUPDistrictlist.add("Meerut");
                        lUPDistrictlist.add("Mirzapur");
                        lUPDistrictlist.add("Moradabad");
                        lUPDistrictlist.add("Muzaffarnagar");
                        lUPDistrictlist.add("Pilibhit");
                        lUPDistrictlist.add("Pratapgarh");
                        lUPDistrictlist.add("Raebareli");
                        lUPDistrictlist.add("Rampur");
                        lUPDistrictlist.add("Saharanpur");
                        lUPDistrictlist.add("Sambhal");
                        lUPDistrictlist.add("Sant Kabir Nagar");
                        lUPDistrictlist.add("Shahjahanpur");
                        lUPDistrictlist.add("Shamli");
                        lUPDistrictlist.add("Shravasti");
                        lUPDistrictlist.add("Siddharthnagar");
                        lUPDistrictlist.add("Sitapur");
                        lUPDistrictlist.add("Sonbhadra");
                        lUPDistrictlist.add("Sultanpur");
                        lUPDistrictlist.add("Unnao");
                        lUPDistrictlist.add("Varanasi");
                        ArrayAdapter<String> lUPAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lUPDistrictlist);
                        lUPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lUPAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lUPAdapter);
                        break;
                    case "West Bengal":
                        List<String> lWBDistrictlist = new ArrayList<String>();
                        lWBDistrictlist.add("Select District");
                        lWBDistrictlist.add("Alipurduar");
                        lWBDistrictlist.add("Bankura");
                        lWBDistrictlist.add("Paschim Bardhaman");
                        lWBDistrictlist.add("Purba Bardhaman");
                        lWBDistrictlist.add("Birbhum");
                        lWBDistrictlist.add("Cooch Behar");
                        lWBDistrictlist.add("Dakshin Dinajpur");
                        lWBDistrictlist.add("Darjeeling");
                        lWBDistrictlist.add("Hooghly");
                        lWBDistrictlist.add("Howrah");
                        lWBDistrictlist.add("Jalpaiguri");
                        lWBDistrictlist.add("Jhargram");
                        lWBDistrictlist.add("Kalimpong");
                        lWBDistrictlist.add("Kolkata");
                        lWBDistrictlist.add("Maldah");
                        lWBDistrictlist.add("Murshidabad");
                        lWBDistrictlist.add("Nadia");
                        lWBDistrictlist.add("North 24 Parganas");
                        lWBDistrictlist.add("Paschim Medinipur");
                        lWBDistrictlist.add("Purba Medinipur");
                        lWBDistrictlist.add("Purulia");
                        lWBDistrictlist.add("South 24 Parganas");
                        lWBDistrictlist.add("Uttar Dinajpur");
                        ArrayAdapter<String> lWBAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, lWBDistrictlist);
                        lWBAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lWBAdapter.notifyDataSetChanged();
                        mDistrictSpinner.setAdapter(lWBAdapter);
                        break;
                     default:
                         List<String> lDfDistrictlist = new ArrayList<String>();
                         lDfDistrictlist.add("Select District");
                         ArrayAdapter<String> lDfAdapter = new ArrayAdapter<String>(this,
                                 android.R.layout.simple_spinner_item, lDfDistrictlist);
                         lDfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                         lDfAdapter.notifyDataSetChanged();
                         mDistrictSpinner.setAdapter(lDfAdapter);
                         break;
                }
            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    public void registerUser(){
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lFullNameString = mFullName.getText().toString();
                String lFatherNameString = mFatherName.getText().toString();
                String lMotherNameString = mMotherName.getText().toString();
                String lMobileNumberString = mMobileNumber.getText().toString();
                String lDOBString = mDOBEditText.getText().toString();
                String lOccupationString = mOccupationEditText.getText().toString();
                String lFlatString = mFlatEditText.getText().toString();
                String lBuildingString = mBuildingEditText.getText().toString();
                String lPinCodeString = mPinCodeEditText.getText().toString();
                String lAreaString = mAreaEditText.getText().toString();
                if (lFullNameString.isEmpty()){
                    mFullName.setError("Please fill the box");
                    mFullName.requestFocus();
                }

                else if(lFatherNameString.isEmpty()){
                    mFatherName.setError("Please fill the box");
                    mFatherName.requestFocus();
                }

                else if(lMotherNameString.isEmpty()){
                    mMotherName.setError("Please fill the box");
                    mMotherName.requestFocus();
                }

                else if (lMobileNumberString.length() == 0){
                    mMobileNumber.setError("Please fill the box");
                    mMobileNumber.requestFocus();
                }

                else if (lDOBString.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Set DOB", Toast.LENGTH_SHORT).show();
                }
                else if (lOccupationString.isEmpty()){
                    mOccupationEditText.setError("Please Fill Box");
//                    mOccupationEditText.requestFocus();
                }

                else if(lFlatString.isEmpty()){
                    mFlatEditText.setError("Please fill the box");
                    mFlatEditText.requestFocus();
                }

                else if (lBuildingString.isEmpty()) {
                    mBuildingEditText.setError("Please fill the box");
                    mBuildingEditText.requestFocus();
                }

                else if (lAreaString.isEmpty()){
                    mAreaEditText.setError("Please fill the box");
                    mAreaEditText.requestFocus();
                }

                else if (lPinCodeString.isEmpty()){
                    mPinCodeEditText.setError("Please fill the box");
                    mPinCodeEditText.requestFocus();
                } else {
                    Log.w(TAG, "Selected Spinner: " + mMaritalSpinner.getAdapter().toString());
                    UserRegisterApi lUserRegisterApi = RetrofitClient.postUserdata();
                    Call<UserRegister> lCallUserResponse = lUserRegisterApi.sendUserData(
                            "20",
                            lFullNameString,
                            lFatherNameString,
                            lMotherNameString,
                            lMobileNumberString,
                            mGender,
                            lDOBString,
                            mMaritalStatus,
                            mEducation,
                            mEducationStatus,
                            mOccupation,
                            lOccupationString,
                            lFlatString,
                            lBuildingString,
                            lAreaString,
                            lPinCodeString,
                            mStateString,
                            mDistrictString);

                    lCallUserResponse.enqueue(new Callback<UserRegister>() {
                        @Override
                        public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                            Log.w("RegisterActivity", "Response: " + response);
                        }

                        @Override
                        public void onFailure(Call<UserRegister> call, Throwable t) {
                            Log.w("RegisterActivity", "Response Failed: " + t.toString() + call.toString());
                        }
                    });
                }
            }
        });
    }

}


