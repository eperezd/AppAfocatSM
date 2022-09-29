package com.afocatsm.prueba1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class listaHositales extends AppCompatActivity implements RecyclerAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {

    private RecyclerView rvLista;
    private SearchView svSearch;
    private RecyclerAdapter adapter;
    private List<ItemList> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_hositales);

        initViews();
        initValues();
        initListener();
    }

    private void initViews(){
        rvLista = findViewById(R.id.rvLista);
        svSearch = findViewById(R.id.svSearch);
    }

    private void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);

        items = getItems();
        adapter = new RecyclerAdapter(items, this);
        rvLista.setAdapter(adapter);
    }

    private void initListener() {
        svSearch.setOnQueryTextListener(this);
    }

    private List<ItemList> getItems() {
        List<ItemList> itemLists = new ArrayList<>();
        itemLists.add(new ItemList("EsSalud Tarapoto", "Somos una institución de seguridad social en salud que brinda una atención integral con calidad y eficiencia para mejorar el bienestar de nuestros asegurados \n" +
                "Sede Central JR. RAMIREZ HURTADO Nº 225 TARAPOTO - SAN MARTIN - Perú\n" +
                "hospital.tarapoto@essalud.gob.pe\n" +
                "Central telefónica\n" +
                "042-529264\n" +
                "http://www.essalud.gob.pe\n" +
                "Av. Vía de Evitamiento 178, Tarapoto ", R.drawable.essaludtarapoto));
        itemLists.add(new ItemList("Minsa Tarapoto", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.minsatarapoto));
        itemLists.add(new ItemList("Centro Traumatologico San Juan", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.sanjuan));
        itemLists.add(new ItemList("Servicios Medicos LUZ DIVINA", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.luzdivina));
        itemLists.add(new ItemList("Clinica San Francisco", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.sanfrancisco));
        itemLists.add(new ItemList("EsSalud JuanJui", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.essaludjuanjui));
        itemLists.add(new ItemList("Minsa Moyobamba", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.minsamoyo));
        itemLists.add(new ItemList("Minsa Tocache", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.minsatocache));
        itemLists.add(new ItemList("Minsa Juanjui - Hospital II", "Somos una institución de seguridad social en salud que brinda una atención integral con calida", R.drawable.minsajuanjui));
       /* itemLists.add(new ItemList("Goku ultrainstinto", "Infaltablñe power-up a Goku.", R.drawable.ultrainsitinto));
        itemLists.add(new ItemList("Super Vegeta Blue x2", "Diferentes transformaciones de super Vegeta.", R.drawable.super_vegeta));
        itemLists.add(new ItemList("Vegeta sapbe", "Vegeta sapbe o no sapbe xD.", R.drawable.vegeta_blue));*/

        return itemLists;
    }

    @Override
    public void itemClick(ItemList item) {
        Intent intent = new Intent(this, detail.class);
        intent.putExtra("itemDetail", item);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }

    public void regresarMain(View view) {
        Intent reportarsiniestro1= new Intent( this, MainActivity.class);
        startActivity(reportarsiniestro1);
    }

}