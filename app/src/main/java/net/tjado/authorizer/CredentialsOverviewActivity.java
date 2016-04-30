package net.tjado.authorizer;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
   
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.text.method.ScrollingMovementMethod;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class CredentialsOverviewActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials_overview);

        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

      
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_credentials_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    
  

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        // Logger
        private static Log log = Log.getInstance();

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_credentials_overview, container, false);

            TextView tv = (TextView) rootView.findViewById(R.id.logTextView);
            tv.setMovementMethod(new ScrollingMovementMethod());

            EditText et = (EditText) rootView.findViewById(R.id.writeTestText);
            et.setText("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()\t -=[]\\;'`,./_+{}|:\"~<>?");

            Button btSendText = (Button) rootView.findViewById(R.id.sendText);
            btSendText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendText();
                }
            });

            Button btSendByte = (Button) rootView.findViewById(R.id.sendByte);
            btSendByte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendByte();
                }
            });

            return rootView;


        }

        public void sendText() {

            EditText et = (EditText) getView().findViewById(R.id.writeTestText);
            String etText =  et.getText().toString();

            try {
                OutputInterface ct = new OutputKeyboard(null);
                ct.sendText(etText);

                TextView tv = (TextView) getView().findViewById(R.id.logTextView);
                tv.setText( log.getLogsString(true) );

                ct.destruct();

                updateSnackbar("Text sent!");
            } catch (Exception e) {
                handleException(e);
            }

        }

        public void sendByte() {

            EditText et = (EditText) getView().findViewById(R.id.writeTestText);
            String etText =  et.getText().toString();

            try {
                OutputInterface ct = new OutputKeyboard(null);
                ((OutputKeyboard) ct).sendScancode( ToolBox.hexStringToByteArray(etText) );

                TextView tv = (TextView) getView().findViewById(R.id.logTextView);
                tv.setText( log.getLogsString(true) );

                ct.destruct();

                updateSnackbar("Byte sent!");
            } catch (Exception e) {
                handleException(e);
            }

        }

        private void handleException(Exception e) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Exception!")
                    .setMessage(e.toString())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        private void updateSnackbar(String msg) {

            Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
}
