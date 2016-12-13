package br.com.conceive.POJO;

import br.com.conceive.R;

/**
 * Created by Denis Viana on 28/11/2016.
 */

public class Item_DashBoard {

    private String itemnome;
    private int cor;

    public String getItemnome() {
        return itemnome;
    }

    public void setItemnome(String itemnome) {
        this.itemnome = itemnome;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public int getIcone(int position){
        switch (position){
            case 0:
                return (R.drawable.project_icon_64);
            case 1:
                return (R.drawable.portfolio_icon_64);
            case 2:
                return (R.drawable.acervo_icon_64);
            default:
                return (R.drawable.settings_icon_64);
        }
    }

}
