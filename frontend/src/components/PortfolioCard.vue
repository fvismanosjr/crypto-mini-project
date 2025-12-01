<script setup lang="ts">
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from '@/components/ui/card'

import {
    Item,
    ItemContent,
    ItemDescription,
    ItemTitle,
    ItemActions,
} from '@/components/ui/item'

import {
    Empty,
    EmptyDescription,
    EmptyHeader,
    EmptyMedia,
    EmptyTitle,
} from '@/components/ui/empty'

</script>

<template>
    <Card>
        <CardHeader>
            <CardTitle>Overview</CardTitle>
            <CardDescription>of your portfolio</CardDescription>
        </CardHeader>
        <CardContent class="flex flex-col gap-3 max-h-[315px] overflow-y-auto">
            <template v-if="portfolios.length">
                <template v-for="portfolio in portfolios" :key="portfolio.id">
                    <Item variant="outline">
                        <ItemContent>
                            <ItemTitle>{{ portfolio.name }}</ItemTitle>
                            <ItemDescription>${{ calculateTotalPerPortfolio(portfolio.holdings) }}</ItemDescription>
                        </ItemContent>
                        <ItemActions>
                            <Button variant="secondary" size="icon-sm"
                                @click.prevent="openPortfolioDialog(portfolio.id, 'edit')">
                                <Pencil />
                            </Button>
                            <Button variant="secondary" size="icon-sm"
                                @click.prevent="openPortfolioDialog(portfolio.id, 'delete')">
                                <X />
                            </Button>
                        </ItemActions>
                    </Item>
                </template>
            </template>
            <Empty v-else>
                <EmptyHeader>
                    <EmptyMedia variant="icon">
                        <BriefcaseBusiness />
                    </EmptyMedia>
                </EmptyHeader>
                <EmptyTitle>No portfolio found</EmptyTitle>
                <EmptyDescription>Add new portfolio to see the overview</EmptyDescription>
            </Empty>
        </CardContent>
    </Card>
</template>
