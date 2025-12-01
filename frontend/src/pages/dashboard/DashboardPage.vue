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

import {
    SidebarInset,
    SidebarProvider,
    SidebarTrigger,
} from "@/components/ui/sidebar"

import {
    BriefcaseBusiness,
    X,
    // Pencil,
    BanknoteArrowDown,
    BanknoteArrowUp,
    Pencil
} from "lucide-vue-next"

import { Button } from '@/components/ui/button'

import AppSidebar from "@/components/AppSidebar.vue"
import AssetTable from "@/components/AssetTable.vue";
import PortfolioDialog from '@/components/PortfolioDialog.vue'
import HoldingDialog from '@/components/HoldingDialog.vue'
import { ref } from 'vue'
import { getPortfoliosWithHoldings } from '@/services/portfolio'
import { getHoldings } from '@/services/holding'
import { getAssets } from '@/services/asset'
import { formatNumberToHuman } from '@/helpers/Number'

const portfolioCardContentKey = ref(0);
const holdingDialogKey = ref(0);
const isHoldingDialogOpen = ref(false);

const portfolioDialogKey = ref(0);
const isPortfolioDialogOpen = ref(false);

interface Portfolio {
    id: number,
    name: string,
    holdings: [],
}

interface Holding {
    id: number,
    asset: string,
    amount: string,
}

interface Asset {
    id: string,
    priceUsd: number,
}

const assets = ref();
const holdingId = ref(0);
const portfolioId = ref(0);
const portfolioType = ref("");
const portfolios = ref<Portfolio[]>([<Portfolio>{
    id: 0,
    name: "",
    holdings: [],
}]);

const holdings = ref<Holding[]>([<Holding>{
    id: 0,
    asset: "",
    amount: "",
}]);

const updateHoldingDialog = (val: boolean) => {
    isHoldingDialogOpen.value = val;
    holdingDialogKey.value++;
}

const updatePortfolioDialog = (val: boolean) => {
    isPortfolioDialogOpen.value = val;
    portfolioDialogKey.value++;
}

getAssets().then((response) => {
    assets.value = response;
})

getPortfoliosWithHoldings().then((response) => {
    portfolios.value = response;
})

getHoldings().then((response) => {
    holdings.value = response;
})

const calculateTotal = () => {
    let total = 0;

    holdings.value.forEach((holding) => {
        if (holding.asset) {
            const find = assets.value.find((asset: Asset) => {
                return asset.id == holding.asset
            });

            total += parseInt(holding.amount) * find.priceUsd
        }
    });


    return formatNumberToHuman(total);
}

const calculateTotalPerPortfolio = (holdings: []) => {
    let total = 0;

    if (holdings) {
        holdings.forEach((holding: Holding) => {
            const find = assets.value.find((asset: Asset) => {
                return asset.id == holding.asset
            });

            total += parseInt(holding.amount) * find.priceUsd;
        })
    }

    return formatNumberToHuman(total);
}

const refresh = async () => {
    portfolios.value = [];

    setTimeout(async () => {
        await getPortfoliosWithHoldings().then((response) => {
            portfolios.value = response;
        })

        await getHoldings().then((response) => {
            holdings.value = response;
        })
    }, 500);
}

const openPortfolioDialog = (id: number, type: string) => {
    portfolioId.value = id;
    portfolioType.value = type;
    isPortfolioDialogOpen.value = true;
    portfolioDialogKey.value++;
}

</script>

<template>
    <SidebarProvider>
        <AppSidebar />
        <SidebarInset>
            <header
                class="flex h-16 shrink-0 items-center gap-2 transition-[width,height] ease-linear group-has-data-[collapsible=icon]/sidebar-wrapper:h-12">
                <div class="flex items-center gap-2 px-4">
                    <SidebarTrigger class="-ml-1" />
                </div>
            </header>
            <div class="flex flex-1 flex-col gap-4 p-4 pt-0">
                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                    <Card>
                        <CardHeader>
                            <CardTitle>Overview</CardTitle>
                            <CardDescription>of everything</CardDescription>
                        </CardHeader>
                        <CardContent class="flex flex-col gap-2 max-h-[315px] overflow-y-auto">
                            <Item variant="muted">
                                <ItemContent>
                                    <ItemTitle class="text-4xl font-medium">{{ portfolios.length }}</ItemTitle>
                                    <ItemDescription>Portfolios</ItemDescription>
                                </ItemContent>
                                <ItemActions>
                                    <PortfolioDialog
                                        :id="portfolioId"
                                        :is-open="isPortfolioDialogOpen"
                                        :type="portfolioType"
                                        :key="`holding-diloag-${portfolioDialogKey}`"
                                        @reload-table="refresh"
                                        @update:open="updatePortfolioDialog"
                                    />
                                </ItemActions>
                            </Item>
                            <Item variant="muted">
                                <ItemContent>
                                    <ItemTitle class="text-4xl font-medium">{{ holdings.length }}</ItemTitle>
                                    <ItemDescription>Holdings</ItemDescription>
                                </ItemContent>
                                <ItemActions>
                                    <HoldingDialog
                                        :id="holdingId"
                                        :is-open="isHoldingDialogOpen"
                                        :key="`holding-diloag-${holdingDialogKey}`"
                                        @reload-table="refresh"
                                        @update:open="updateHoldingDialog"
                                    />
                                </ItemActions>
                            </Item>
                            <Item variant="muted">
                                <ItemContent>
                                    <ItemTitle class="text-4xl font-medium">${{ calculateTotal() }}</ItemTitle>
                                    <ItemDescription>Total balance</ItemDescription>
                                </ItemContent>
                            </Item>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Overview</CardTitle>
                            <CardDescription>of your portfolio</CardDescription>
                        </CardHeader>
                        <CardContent
                            class="flex flex-col gap-3 max-h-[315px] overflow-y-auto"
                            :key="`card-content-${portfolioCardContentKey}`"
                        >
                            <template v-if="portfolios.length">
                                <template v-for="portfolio in portfolios" :key="portfolio.id">
                                    <Item variant="outline">
                                        <ItemContent>
                                            <ItemTitle>{{ portfolio.name }}</ItemTitle>
                                            <ItemDescription>${{ calculateTotalPerPortfolio(portfolio.holdings) }}</ItemDescription>
                                        </ItemContent>
                                        <ItemActions>
                                            <Button variant="secondary" size="icon-sm" @click.prevent="openPortfolioDialog(portfolio.id, 'edit')">
                                                <Pencil />
                                            </Button>
                                            <Button variant="secondary" size="icon-sm" @click.prevent="openPortfolioDialog(portfolio.id, 'delete')">
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
                    <Card>
                        <CardHeader>
                            <CardTitle>Overview</CardTitle>
                            <CardDescription>of your holdings</CardDescription>
                        </CardHeader>
                        <CardContent class="flex flex-col gap-3 max-h-[315px] overflow-y-auto">
                            <template v-if="holdings.length">
                                <template v-for="holding in holdings" :key="holding.id">
                                    <Item variant="outline">
                                        <ItemContent>
                                            <ItemTitle>{{ holding.asset }}</ItemTitle>
                                            <ItemDescription>{{ holding.amount }}</ItemDescription>
                                        </ItemContent>
                                        <ItemActions>
                                            <Button variant="secondary" size="icon-sm">
                                                <BanknoteArrowUp />
                                            </Button>
                                            <Button variant="secondary" size="icon-sm">
                                                <BanknoteArrowDown />
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
                                <EmptyTitle>No holdings found</EmptyTitle>
                                <EmptyDescription>Add new holding to see the overview</EmptyDescription>
                            </Empty>
                        </CardContent>
                    </Card>
                </div>
                <div class="grid grid-cols-1">
                    <AssetTable></AssetTable>
                </div>
            </div>
        </SidebarInset>
    </SidebarProvider>
</template>
