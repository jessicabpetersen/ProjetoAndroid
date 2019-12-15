package com.example.projeto;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public abstract class MainActionModeCallback implements ActionMode.Callback {
    private ActionMode actionMode;
    private MenuItem countItem;

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.main_action_mode_callback,menu);
        this.actionMode = actionMode;
        this.countItem = menu.findItem(R.id.action_checked_count);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    public void setCount(String checkedCount){
        if(countItem != null)
            this.countItem.setTitle(checkedCount);
    }

    public ActionMode getActionMode(){
        return actionMode;
    }
}
