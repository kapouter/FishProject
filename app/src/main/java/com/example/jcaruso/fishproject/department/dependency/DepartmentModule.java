package com.example.jcaruso.fishproject.department.dependency;

import com.example.jcaruso.fishproject.department.view.DepartmentsPresenter;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class DepartmentModule {

    @DepScope
    @Provides
    public DepartmentsPresenter providesDepartmentsPresenter(DataService dataService, BaseSchedulerProvider schedulerProvider) {
        return new DepartmentsPresenter(dataService, schedulerProvider);
    }
}
