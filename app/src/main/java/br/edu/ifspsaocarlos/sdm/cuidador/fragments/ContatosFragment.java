package br.edu.ifspsaocarlos.sdm.cuidador.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.cuidador.R;
import br.edu.ifspsaocarlos.sdm.cuidador.activities.ContatosActivity;
import br.edu.ifspsaocarlos.sdm.cuidador.adapters.ContatoAdapter;
import br.edu.ifspsaocarlos.sdm.cuidador.data.CuidadorFirebaseRepository;
import br.edu.ifspsaocarlos.sdm.cuidador.entities.Contato;
import br.edu.ifspsaocarlos.sdm.cuidador.interfaces.RecyclerViewOnItemSelecionado;

/**
 * Fragment da lista de contatos.
 *
 * @author Anderson Canale Garcia
 */
public class ContatosFragment extends Fragment implements RecyclerViewOnItemSelecionado {

    private RecyclerView mRecyclerView;
    private List<Contato> listaContatos;
    ContatosActivity contatosActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        contatosActivity = (ContatosActivity) getActivity();
        contatosActivity.getSupportActionBar().setTitle(getString(R.string.gerenciar_contatos));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_contatos);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        listaContatos = CuidadorFirebaseRepository.getInstance().getContatos();
        ContatoAdapter adapter = new ContatoAdapter(getActivity(), listaContatos);
        adapter.setRecyclerViewOnItemSelecionado(this);
        mRecyclerView.setAdapter(adapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        view.findViewById(R.id.btn_cadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroContatoFragment fragment = CadastroContatoFragment.newInstance("", "", "");
                contatosActivity.openFragment(fragment);
            }

        });

        return view;
    }

    @Override
    public void onItemSelecionado(View view, int posicao) {

        Contato contato = listaContatos.get(posicao);

        CadastroContatoFragment fragment = CadastroContatoFragment.newInstance(contato.getId(), contato.getNome(), contato.getTelefone());
        contatosActivity.openFragment(fragment);
    }

    public static ContatosFragment newInstance() {
        ContatosFragment fragment = new ContatosFragment();
        return fragment;
    }
}