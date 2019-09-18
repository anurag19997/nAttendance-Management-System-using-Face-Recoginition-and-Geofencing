//package com.example.myapplication;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.support.design.widget.Snackbar;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class Custom_Calling_Adapter extends ArrayAdapter<ContactsModel> implements View.OnClickListener {
//
//    private ArrayList<ContactsModel> dataList;
//    Context mContext;
//    private static LayoutInflater inflater=null;
//    ContactsModel tempContactsModel;
//
//    @Override
//    public void onClick(View v) {
//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        ContactsModel contactsModel=(ContactsModel)object;
//
//        switch (v.getId())
//        {
//            case R.id.caller_name_text:
//                Snackbar.make(v, "Release date " +contactsModel.getName(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
//
//    }
//
//    public static class ViewHolder{
//
//        public TextView name_text;
//        public TextView description_text;
//        public ImageView caller_image;
//        public ImageView dial_icon_image;
//
//    }
//
//    public Custom_Calling_Adapter(Context context, int resource, ArrayList<ContactsModel> objects) {
//        super(context, resource, objects);
//        this.dataList = objects;
//        this.mContext=context;
//        inflater = ( LayoutInflater )getContext().
//                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//    //@androidx.annotation.NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //return super.getView(position, convertView, parent);
//
//        View vi = convertView;
//        ViewHolder holder;
//
//        if(convertView==null) {
//            vi = inflater.inflate(R.layout.list_row, null);
//
//            /****** View Holder Object to contain tabitem.xml file elements ******/
//
//            holder = new ViewHolder();
//            holder.name_text = (TextView) vi.findViewById(R.id.caller_name_text);
//            holder.description_text=(TextView)vi.findViewById(R.id.description_text);
//            holder.caller_image=(ImageView)vi.findViewById(R.id.caller_image);
//            holder.dial_icon_image = (ImageView)vi.findViewById(R.id.dialer_icon_image);
////            SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
////            SharedPreferences.Editor editor = pref.edit();
////            String time = pref.getString("Time", "19:20 Tue");
////            String phone = pref.getString("phone_no", "9013356899");
////            holder.description_text.setText(time + phone);
//
//
//            /************  Set holder with LayoutInflater ************/
//            vi.setTag( holder );
//        }
//        else{
//            holder=(ViewHolder)vi.getTag();
//            tempContactsModel = null;
//            tempContactsModel = (ContactsModel) dataList.get(position);
//            holder.name_text.setText(tempContactsModel.getName());
//            holder.description_text.setText(tempContactsModel.getContact_number() + tempContactsModel.getTime_to_call());
//            holder.caller_image.setImageResource(R.drawable.baseline_contacts);
//            vi.setOnClickListener(this);
//
//        }
//        return vi;
//    }
//    //
////    public Custom_Calling_Adapter(Context context, ArrayList<String> arrli) {
////        super(context, R.layout.list_row ,arrli);
////    }
//}
//
