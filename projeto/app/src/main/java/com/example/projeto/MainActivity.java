package com.example.projeto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LivroEventListener, Drawer.OnDrawerItemClickListener{
    private RecyclerView recyclerView;
    private ArrayList<Livro> lista_livros;
    private LivrosAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private MainActionModeCallback mainActionModeCallback;
    private LivroDao dao;
    private int checkedCount = 0;
    private SharedPreferences settings;
    private int theme;
    public static final String THEME_Key = "app_theme";
    public static final String APP_PREFERENCES="books_settings";
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        theme = settings.getInt(THEME_Key, R.style.AppTheme);
        setTheme(theme);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setupNavigation(savedInstanceState, toolbar);


        recyclerView = findViewById(R.id.list_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 13/05/2018  add new note
                onAddNewNote();
            }
        });
        dao = LivroRoom.getInstance(this).livroDao();

    }

    public void onAddNewNote(){
        startActivity(new Intent(this, EditBookActivity.class));
    }

    private void setupNavigation(Bundle savedInstanceState, Toolbar toolbar){
        List<IDrawerItem> iDrawerItems = new ArrayList<>();
        iDrawerItems.add(new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home_black_24dp));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Livros").withIcon(R.drawable.ic_library_books_black_24dp));

        List<IDrawerItem> stockyItems = new ArrayList<>();

        SwitchDrawerItem switchDrawerItem = new SwitchDrawerItem()
                .withName("Dark Theme")
                .withChecked(theme == R.style.AppTheme_Dark)
                .withIcon(R.drawable.dark_theme)
                .withOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                        // TODO: 02/10/2018 change to darck theme and save it to settings
                        if (isChecked) {
                            settings.edit().putInt(THEME_Key, R.style.AppTheme_Dark).apply();
                        } else {
                            settings.edit().putInt(THEME_Key, R.style.AppTheme).apply();
                        }

                        // recreate app or the activity // if it's not working follow this steps
                        // MainActivity.this.recreate();

                        // this lines means wi want to close the app and open it again to change theme
                        TaskStackBuilder.create(MainActivity.this)
                                .addNextIntent(new Intent(MainActivity.this, MainActivity.class))
                                .addNextIntent(getIntent()).startActivities();
                    }
                });


        AccountHeader header = new AccountHeaderBuilder().withActivity(this)
                .addProfiles(new ProfileDrawerItem()
                        .withEmail("feedback.mrzero@gmail.com")
                        .withName("ixiDev")
                        .withIcon(R.mipmap.ic_launcher_round))
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .withSelectionListEnabledForSingleProfile(false) // we need just one profile
                .build();

        // Navigation drawer
//        new DrawerBuilder()
//                .withActivity(this) // activity main
//                .withSavedInstance(savedInstanceState) // saveInstance of activity
//                .withDrawerItems(iDrawerItems) // menu items
//                .withTranslucentNavigationBar(true)
//                .withStickyDrawerItems(stockyItems) // footer items
//                .withAccountHeader(header) // header of navigation
//                .withOnDrawerItemClickListener((Drawer.OnDrawerItemClickListener) this) // listener for menu items click
//                .build();


    }

    private void loadBooks() {
        this.lista_livros = new ArrayList<>();
        List<Livro> list = dao.getLivro();// get All notes from DataBase
        this.lista_livros.addAll(list);
        this.adapter = new LivrosAdapter(this, this.lista_livros);
        // set listener to adapter
        this.adapter.setListener((LivroEventListener) this);
        this.recyclerView.setAdapter(adapter);
        showEmptyView();
        // add swipe helper to recyclerView

//        swipeToDeleteHelper.attachToRecyclerView(recyclerView);
    }

    private void showEmptyView() {
        if (lista_livros.size() == 0) {
            this.recyclerView.setVisibility(View.GONE);
            findViewById(R.id.empty_books_view).setVisibility(View.VISIBLE);

        } else {
            this.recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.empty_books_view).setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }


    public void onNoteClick(Livro note) {
        // TODO: 22/07/2018  note clicked : edit note
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BookFragment fragment = new BookFragment();
        fragmentTransaction.add(R.id.activity_main, fragment).commit();

    }

    @Override
    public void onLivroClick(Livro livro) {

    }

    @Override
    public void onLivroLongClick(Livro livro) {

    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
