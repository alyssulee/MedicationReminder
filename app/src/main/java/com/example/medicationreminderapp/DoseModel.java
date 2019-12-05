package com.example.medicationreminderapp;

import java.util.ArrayList;

    public class DoseModel {
        private String mName;
        private int mAmount;
        private boolean mOnline;

        public DoseModel(int amount, String name, boolean online) {
            mAmount = amount;
            mName = name;
            mOnline = online;
        }

        public String getName() {
            return mName;
        }

        public int getmAmount()
        {
            return mAmount;
        }

        public boolean isOnline() {
            return mOnline;
        }

        public void toggleOnline()
        {
            mOnline = !mOnline;
        }

        private static int lastContactId = 0;

    }
