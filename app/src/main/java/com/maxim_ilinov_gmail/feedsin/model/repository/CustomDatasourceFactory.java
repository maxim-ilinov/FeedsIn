package com.maxim_ilinov_gmail.feedsin.model.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class CustomDatasourceFactory extends DataSource.Factory {
    @NonNull
    @Override
    public CustomDatasource create() {
        return new CustomDatasource();
    }
}
