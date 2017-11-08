package br.com.listamercado.app;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list_view;
    EditText txt_produto;
    ProdutoAdapter adapter;

    String teste;

    View.OnClickListener click_ck = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            CheckBox ck = (CheckBox) view;

            int posicao = (int) ck.getTag();

            Produto produtoSelecionado = adapter.getItem(posicao);
            produtoSelecionado.setAtivo(true);

            Produto produtoDB = Produto.findById(Produto.class,produtoSelecionado.getId());

            if (ck.isChecked()){
                produtoDB.setAtivo(true);
                produtoDB.save();

                Toast.makeText(getApplicationContext(),"Checado",Toast.LENGTH_LONG);
            } else {
                produtoDB.setAtivo(false);
                produtoDB.save();

                Toast.makeText(getApplicationContext(),"Não Checado",Toast.LENGTH_LONG);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView) findViewById(R.id.list_view);
        txt_produto = (EditText) findViewById(R.id.txtProduto);

        List<Produto> lstProdutos = Produto.listAll(Produto.class);

        //Dados Fake
/*
        lstProdutos.add(new Produto("Arroz", false));
        lstProdutos.add(new Produto("Feijão", false));
        lstProdutos.add(new Produto("Macarrão", true));
*/
        //Criar o adapter
        adapter = new ProdutoAdapter(this, lstProdutos);

        //ligar o adapter a list_view
        list_view.setAdapter(adapter);


    }



    public void inserirItem(View view) {

        String produto = txt_produto.getText().toString();

        if(produto.isEmpty()) return;

        //Criando o obj produto
        Produto p = new Produto(produto, false);

        //adicionando na lista
        adapter.add(p);

        //Adicionando no banco
        p.save();

        //limpar a caixinha
        txt_produto.setText(null);

    }

    //Classe do Adapter
    private class ProdutoAdapter extends ArrayAdapter<Produto>{

        public ProdutoAdapter(Context ctx, List<Produto> items){
            super(ctx, 0, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,
                            @NonNull ViewGroup parent) {

            View v = convertView;

            if (v == null){

                v = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_lista, null);
            }

            Produto item = getItem(position);

            TextView txt_item_produto = v.findViewById(R.id.txt_item_produto);
            CheckBox ck_item_produto = v.findViewById(R.id.ok_item_produto);

            txt_item_produto.setText(item.getNome());
            ck_item_produto.setChecked(item.isAtivo());


            // GUARDANDO A POSICAO DO ITEM
            ck_item_produto.setTag(position);

            ck_item_produto.setOnClickListener(click_ck);
            return v;
        }
    }

}