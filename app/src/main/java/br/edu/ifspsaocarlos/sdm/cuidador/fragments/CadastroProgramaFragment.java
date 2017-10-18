package br.edu.ifspsaocarlos.sdm.cuidador.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.ifspsaocarlos.sdm.cuidador.R;
import br.edu.ifspsaocarlos.sdm.cuidador.activities.ProgramasActivity;
import br.edu.ifspsaocarlos.sdm.cuidador.entities.Programa;
import br.edu.ifspsaocarlos.sdm.cuidador.services.CuidadorService;

/**
 * Fragment de cadastro de programa.
 *
 * @author Anderson Canale Garcia
 */

public class CadastroProgramaFragment  extends Fragment {
    private static final String PROGRAMA = "PROGRAMA";

    private Programa programa;

    private CadastroProgramaFragment.OnFragmentInteractionListener mListener;
    private CuidadorService cuidadorService;

    public CadastroProgramaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param programa Instância do programa
     * @return Uma nova instância do fragment CadastroProgramaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroProgramaFragment newInstance(Programa programa) {
        CadastroProgramaFragment fragment = new CadastroProgramaFragment();
        Bundle args = new Bundle();
        args.putSerializable(PROGRAMA, programa);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cuidadorService = new CuidadorService(getActivity());
        if (getArguments() != null) {
            programa = (Programa) getArguments().getSerializable(PROGRAMA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_programa, container, false);
        setHasOptionsMenu(true);

        final ProgramasActivity activity = (ProgramasActivity) getActivity();
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((EditText)view.findViewById(R.id.programa_nome)).setText(programa.getNome());
        ((EditText)view.findViewById(R.id.programa_horarios)).setText(programa.getHorarios());
        ((EditText)view.findViewById(R.id.programa_link)).setText(programa.getLink());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_cadastro ,menu);
        menu.findItem(R.id.excluir).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.salvar:
                String nome = ((TextView)getView().findViewById(R.id.programa_nome)).getText().toString();
                String horarios = ((TextView)getView().findViewById(R.id.programa_horarios)).getText().toString();
                String link = ((TextView)getView().findViewById(R.id.programa_link)).getText().toString();

                Programa programa = new Programa();
                programa.setId(this.programa.getId());
                programa.setNome(nome);
                programa.setHorarios(horarios);
                programa.setLink(link);

                cuidadorService.salvarPrograma(programa);

                redirecionaParaLista();
                break;
            case R.id.excluir:
                cuidadorService.removerPrograma(this.programa.getId());
                redirecionaParaLista();
                break;
            case android.R.id.home:
                redirecionaParaLista();
                break;
        }

        //Toast.makeText(this, msg + " clicked !", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    private void redirecionaParaLista() {
        Intent loginIntent = new Intent(getActivity(), ProgramasActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CadastroProgramaFragment.OnFragmentInteractionListener) {
            mListener = (CadastroProgramaFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}