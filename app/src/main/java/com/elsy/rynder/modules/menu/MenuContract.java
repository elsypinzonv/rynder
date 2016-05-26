package com.elsy.rynder.modules.menu;

public class MenuContract {

    interface View {

        void changeRightPage();

        void changeLeftPage();

    }

    interface UserActionsListener {

        void unRegisterListener();

    }

}
