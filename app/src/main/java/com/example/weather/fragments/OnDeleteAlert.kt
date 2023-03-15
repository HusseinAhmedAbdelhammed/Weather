package com.example.weather.fragments

import com.example.domain.entity.fakeentity.AlertDomainEntity

interface OnDeleteAlert {
    fun delete(alert: AlertDomainEntity)
}