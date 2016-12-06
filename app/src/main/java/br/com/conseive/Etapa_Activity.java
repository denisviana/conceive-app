package br.com.conseive;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.conseive.POJO.Anotacao;
import br.com.conseive.POJO.Etapa_Projeto;
import br.com.conseive.adapter.Adapter_Anotacoes;

public class Etapa_Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private Toolbar toolbar_bottom;
    private Etapa_Projeto etapa;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Adapter_Anotacoes adapter;
    private RecyclerView list_anotacoes;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);
        initViews();



        if(getIntent().getExtras()!=null){
            etapa = (Etapa_Projeto) getIntent().getSerializableExtra("etapa");
        }

        toolbar_bottom.inflateMenu(R.menu.menu_etapa_arquivos_bottom);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(etapa.getNome_etapa());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        switch(mViewPager.getCurrentItem()){
            case 1:
                toolbar_bottom.inflateMenu(R.menu.menu_etapa_arquivos_bottom);
                break;
            case 2:

        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);





    }

    public void initViews(){
        toolbar_bottom = (Toolbar) findViewById(R.id.toolbar);
        list_anotacoes = (RecyclerView) findViewById(R.id.list_anotacoes);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_etapa_arquivos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
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
        private RecyclerView list_anotacoes;

        public PlaceholderFragment() {

        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_etapa_anotacoes, container, false);

            list_anotacoes = (RecyclerView) rootView.findViewById(R.id.list_anotacoes);
            list_anotacoes.setHasFixedSize(true);
            list_anotacoes.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });


            ArrayList<Anotacao> anotacoes = new ArrayList<>();

            Anotacao anotacao = new Anotacao();
            anotacao.setNome("Denis Viana");
            anotacao.setData("01 de dez");
            anotacao.setAnotacao("Anotação de teste para testar o CardView no Android");
            anotacao.setHora("10:12");
            anotacoes.add(anotacao);

            anotacao = new Anotacao();
            anotacao.setNome("Denis Viana");
            anotacao.setData("01 de dez");
            anotacao.setAnotacao("Anotação de teste para testar o CardView no Android");
            anotacao.setHora("10:12");
            anotacoes.add(anotacao);

            anotacao = new Anotacao();
            anotacao.setNome("Denis Viana");
            anotacao.setData("01 de dez");
            anotacao.setAnotacao("Anotação de teste para testar o CardView no Android");
            anotacao.setHora("10:12");
            anotacoes.add(anotacao);

            anotacao = new Anotacao();
            anotacao.setNome("Denis Viana");
            anotacao.setData("01 de dez");
            anotacao.setAnotacao("Anotação de teste para testar o CardView no Android");
            anotacao.setHora("10:12");
            anotacoes.add(anotacao);

            anotacao = new Anotacao();
            anotacao.setNome("Denis Viana");
            anotacao.setData("01 de dez");
            anotacao.setAnotacao("Anotação de teste para testar o CardView no Android");
            anotacao.setHora("10:12");
            anotacoes.add(anotacao);

            anotacao = new Anotacao();
            anotacao.setNome("Ramon Felipe");
            anotacao.setData("03 de dez");
            anotacao.setAnotacao("Anotação de teste para testar o CardView no Android " +
                    "Anotação de teste para testar o CardView no AndroidAnotação de teste para testar o " +
                    "CardView no AndroidAnotação de teste para testar o CardView no AndroidAnotação de " +
                    "teste para testar o CardView no AndroidAnotação de teste para testar o CardView no Android");
            anotacao.setHora("10:12");
            anotacoes.add(anotacao);

            Adapter_Anotacoes adapter = new Adapter_Anotacoes(anotacoes,getActivity());

            list_anotacoes.setAdapter(adapter);



            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            list_anotacoes.setLayoutManager(layoutManager);



            int pageNumber = getArguments().getInt(ARG_SECTION_NUMBER);



            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, pageNumber));
            return rootView;
        }
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
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Arquivos";
                case 1:
                    return "Anotações";

            }
            return null;
        }
    }
}
