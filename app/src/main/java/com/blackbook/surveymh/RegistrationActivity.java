package com.blackbook.surveymh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackbook.surveymh.Constant.AppConstant;
import com.blackbook.surveymh.Constant.AppGlobal;
import com.blackbook.surveymh.Constant.WsConstant;
import com.blackbook.surveymh.asynktask.AsyncPostService;
import com.blackbook.surveymh.interfaces.WsResponseListener;
import com.blackbook.surveymh.model.Common;
import com.blackbook.surveymh.model.ResponseObject;
import com.blackbook.surveymh.model.ResponseResult;
import com.blackbook.surveymh.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 *
 * Created by c119 on 31/03/16.
 *
 */
public class RegistrationActivity extends BaseActivity implements View.OnClickListener, WsResponseListener
{
    public TextView txtresult, txtblackbook, txtparticipation, txtvarify, txtcontactby, txtemailby, txttelephoneby;
    public EditText edtname, edtemail, edtphone;
    public Button btnsubmit;
    public CheckBox chkresult, chkblackbook, chkparticipation, chkemail, chktelephone;
    public ArrayList<Integer> arr_agreement;
    public String sendpref, senduserpref;

    public RelativeLayout relaeditext;
    public User obj;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        arr_agreement = new ArrayList<>(5);
        for(int i = 0 ; i < 5 ; i++)
        {
            arr_agreement.add(108);
        }

        initview();
    }

    private void initview()
    {
        relaeditext = (RelativeLayout)findViewById(R.id.rela_editext);

        edtname = (EditText)findViewById(R.id.edt_name);
        edtemail = (EditText)findViewById(R.id.edt_email);
        edtphone = (EditText)findViewById(R.id.edt_phone);

        edtname.setTypeface(Sufi_Regular);
        edtemail.setTypeface(Sufi_Regular);
        edtphone.setTypeface(Sufi_Regular);

        chkresult = (CheckBox)findViewById(R.id.chk_result);
        chkblackbook = (CheckBox)findViewById(R.id.chk_blackbook);
        chkparticipation = (CheckBox)findViewById(R.id.chk_participation);
        chkemail = (CheckBox)findViewById(R.id.chk_email);
        chktelephone = (CheckBox)findViewById(R.id.chk_telephone);

        chkresult.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(chkresult.isChecked())
                {
                    arr_agreement.set(0,1);
                }
                else
                {
                    arr_agreement.set(0,108);
                }
            }
        });

        chkblackbook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(chkblackbook.isChecked())
                {
                    arr_agreement.set(1,1);
                }
                else
                {
                    arr_agreement.set(1,108);
                }
            }
        });

        chkparticipation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(chkparticipation.isChecked())
                {
                    arr_agreement.set(2,1);
                }
                else
                {
                    arr_agreement.set(2,108);
                }
            }
        });

        chkemail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(chkemail.isChecked())
                {
                    arr_agreement.set(3,1);
                }
                else
                {
                    arr_agreement.set(3,108);
                }
            }
        });

        chktelephone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(chktelephone.isChecked())
                {
                    arr_agreement.set(4,1);
                }
                else
                {
                    arr_agreement.set(4,108);
                }
            }
        });

        txtresult = (TextView)findViewById(R.id.txt_result);
        txtblackbook = (TextView)findViewById(R.id.txt_blackbook);
        txtparticipation = (TextView)findViewById(R.id.txt_participation);
        txtvarify = (TextView)findViewById(R.id.txt_varify);
        txtcontactby = (TextView)findViewById(R.id.txt_contactby);
        txtemailby = (TextView)findViewById(R.id.txt_emailby);
        txttelephoneby = (TextView)findViewById(R.id.txt_telephoneby);

        txtresult.setTypeface(Sufi_Regular);
        txtblackbook.setTypeface(Sufi_Regular);
        txtparticipation.setTypeface(Sufi_Regular);
        txtvarify.setTypeface(Sufi_Regular);
        txtcontactby.setTypeface(Sufi_Regular);
        txtemailby.setTypeface(Sufi_Regular);
        txttelephoneby.setTypeface(Sufi_Regular);

        btnsubmit = (Button)findViewById(R.id.btn_submit);

        btnsubmit.setOnClickListener(this);
        btnsubmit.setTypeface(Sufi_Regular);

        Gson gson = new Gson();
        String json = AppGlobal.getStringPreference(this, AppConstant.PREF_USER_OBJ);
        obj = gson.fromJson(json, User.class);

        if (obj != null) {
            if (obj.getUsername() != null) {
                edtname.setText(obj.getUsername());
            }

            if (obj.getEmail_id() != null) {
                edtemail.setText(obj.getEmail_id());
            }

            if (obj.getPhone_number() != null) {
                edtphone.setText(obj.getPhone_number());
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_submit:

                StringBuilder sbpref = new StringBuilder();
                StringBuilder sbuserpref = new StringBuilder();

                int arrsize = arr_agreement.size();
                for(int n = 0 ; n < arrsize ; n++)
                {
                    if(arr_agreement.get(n) != 108)
                    {
                        sbpref.append(String.valueOf(n+1));
                        sbpref.append(",");
                    }
                }

                if(sbpref.toString().length() > 0)
                {
                    sendpref = sbpref.toString().substring(0,sbpref.toString().length()-1);
                    String[] myarr = sendpref.split(",");
                    for(String pref : myarr)
                    {
                        sbuserpref.append("1");
                        sbuserpref.append(",");
                    }

                    senduserpref = sbuserpref.toString().substring(0,sbuserpref.toString().length()-1);
                }
                else
                {
                    sendpref = "";
                    senduserpref = "";
                }

                if (validForm()) {
                    if(obj == null && AppGlobal.getBooleanPreference(this, AppConstant.PREF_GUESTLOGIN)){
                        Common cm = new Common();
                        cm.setUserID("");

                        cm.setUsername(edtname.getText().toString().trim());
                        cm.setEmailID(edtemail.getText().toString().trim());
                        cm.setPhone(edtphone.getText().toString().trim());
                        cm.setDeviceToken("");
                        cm.setDeviceType("");
                        if (AppGlobal.isNetwork(RegistrationActivity.this))
                        {
                            try
                            {
                                new AsyncPostService(RegistrationActivity.this,getResources().getString(R.string.Saving_profile), WsConstant.Req_Save_Profile,cm,true,true)
                                        .execute(WsConstant.WS_SAVE_PROFILE);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            AppGlobal.showToast(RegistrationActivity.this, getResources().getString(R.string.str_no_internet));
                        }
                    }else{
                        Intent i = new Intent(RegistrationActivity.this, PresurveyActivity.class);
                        startActivity(i);
                    }
                }

                AppGlobal.setStringPreference(this, sendpref, AppConstant.Prefsendpref);
                AppGlobal.setStringPreference(this, senduserpref, AppConstant.Prefsenduserpref);

                break;
        }
    }

    private boolean validForm() {

        if (AppGlobal.getBooleanPreference(this, AppConstant.PREF_GUESTLOGIN)) {
            if (edtname.getText().toString().length() <= 0 || edtname.getText().toString().equals(" ")
                    || edtname.getText().toString().equals("")) {
                AppGlobal.showToast(this, getResources().getString(R.string.str_enter_name));

                return false;
            }


            if (edtemail.getText().toString().length() <= 0 || edtemail.getText().toString().equals(" ")
                    || edtemail.getText().toString().equals("")) {
                AppGlobal.showToast(this, getResources().getString(R.string.str_enter_email));

                return false;
            }


            if (!AppGlobal.checkEmail(edtemail.getText().toString())) {
                AppGlobal.showToast(this, getResources().getString(R.string.str_enter_validemail));

                return false;
            }


            if (edtphone.getText().toString().length() <= 0 || edtphone.getText().toString().equals(" ")
                    || edtphone.getText().toString().equals("")) {
                AppGlobal.showToast(this, getResources().getString(R.string.str_enter_phone));

                return false;
            }

            if (!AppGlobal.checkPhone(edtphone.getText().toString())) {
                AppGlobal.showToast(this, getResources().getString(R.string.str_enter_validphone));

                return false;
            }
        }


        /*if(!chkresult.isChecked())
        {
            AppGlobal.showToast(this, getResources().getString(R.string.str_check_notice));

            return false;
        }

        if(!chkblackbook.isChecked())
        {
            AppGlobal.showToast(this, getResources().getString(R.string.str_check_incentives));

            return false;
        }


        if(!chkparticipation.isChecked())
        {
            AppGlobal.showToast(this, getResources().getString(R.string.str_check_participation));

            return false;
        }*/

        return true;
    }

    @Override
    public void onDelieverResponse(String serviceType, Object data, Exception error) {
        if (error == null)
        {
            if (serviceType.equalsIgnoreCase(WsConstant.Req_Save_Profile))
            {
                ResponseObject resObj = ((ResponseResult) data).getResult();
                if(resObj.getError_status().equalsIgnoreCase("NO"))
                {
                    User uobj = new User();
                    uobj.setId(resObj.getArr_user().get(0).getId());
                    uobj.setUsername(resObj.getArr_user().get(0).getUsername());
                    uobj.setFacebook_id(resObj.getArr_user().get(0).getFacebook_id());
                    uobj.setPassword(resObj.getArr_user().get(0).getPassword());
                    uobj.setFirstname(resObj.getArr_user().get(0).getFirstname());
                    uobj.setLastname(resObj.getArr_user().get(0).getLastname());
                    uobj.setEmail_id(resObj.getArr_user().get(0).getEmail_id());
                    uobj.setPhone_number(resObj.getArr_user().get(0).getPhone_number());
                    uobj.setDevice_token(resObj.getArr_user().get(0).getDevice_token());
                    uobj.setDevice_type(resObj.getArr_user().get(0).getDevice_type());
                    uobj.setCreated_date(resObj.getArr_user().get(0).getCreated_date());
                    uobj.setModified_date(resObj.getArr_user().get(0).getModified_date());
                    uobj.setIs_deleted(resObj.getArr_user().get(0).getIs_deleted());

                    if (!AppGlobal.getStringPreference(this,AppConstant.PREF_USER_OBJ).equals(""))
                    {
                        AppGlobal.removepref(this, AppConstant.PREF_USER_OBJ);
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(uobj);
                    AppGlobal.setStringPreference(this,json,AppConstant.PREF_USER_OBJ);

                    AppGlobal.setBooleanPreference(this,true,AppConstant.PREF_USERLOGIN);

                    Intent i = new Intent(RegistrationActivity.this, PresurveyActivity.class);
                    startActivity(i);
                }
            }
        }
    }
}
