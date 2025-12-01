<script setup lang="ts">
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
    DialogClose,
} from '@/components/ui/dialog'

import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from '@/components/ui/select'

import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Button } from '@/components/ui/button'
import { Plus } from "lucide-vue-next"

import { getAssets } from '@/services/asset';
import { ref, watch } from 'vue';
import { findHolding, saveHolding } from '@/services/holding'
import { getPortfolios } from '@/services/portfolio'

const props = defineProps<{
    id?: number,
    isOpen: boolean
}>()

const emit = defineEmits<{
    (e: "update:open", value: boolean): void,
    (e: "reload-table", value: boolean): void,
}>();

interface Asset {
    id: string,
    priceUsd: number,
}

const holdingForm = ref({
    asset: "",
    amount: 0,
    stock: 0,
    portfolioId: "",
});

const portfolios = ref();
const assets = ref();

watch(() => holdingForm.value.amount, () => {
    calculateStock()
})

watch(() => holdingForm.value.asset, () => {
    calculateStock()
})

if (props.isOpen) {
    getAssets().then((response) => {
        assets.value = response;
    });

    getPortfolios().then((response) => {
        portfolios.value = response;
    })

    if (props.id) {
        findHolding(props.id).then((result) => {
            holdingForm.value = result;
        })
    }
}

const calculateStock = () => {
    if (holdingForm.value.asset && holdingForm.value.amount) {
        const filteredAsset = assets.value.find((asset: Asset) => {
            return asset.id == holdingForm.value.asset
        });

        holdingForm.value.stock = holdingForm.value.amount / filteredAsset.priceUsd;
    }
}

const submit = () => {
    saveHolding(holdingForm.value).then(() => {
        emit("update:open", false);
        emit("reload-table", true);
    });
}

</script>

<template>
    <Dialog :open="isOpen" @update:open="(val) => emit('update:open', val)">
        <DialogTrigger>
            <Button variant="outline" size="icon-sm">
                <Plus />
            </Button>
        </DialogTrigger>
        <DialogContent>
            <DialogHeader>
                <DialogTitle>Holding</DialogTitle>
                <DialogDescription>Donec quis arcu condimentum, egestas elit tempus, varius sapien. </DialogDescription>
            </DialogHeader>
            <div class="grid gap-4">
                <div class="grid gap-3">
                    <Label for="portfolio">Portfolio</Label>
                    <Select v-model="holdingForm.portfolioId">
                        <SelectTrigger class="w-full">
                            <SelectValue placeholder="Select portfolio" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem v-for="portfolio in portfolios" :key="portfolio.id" :value="portfolio.id">
                                {{ portfolio.name }}
                            </SelectItem>
                        </SelectContent>
                    </Select>
                </div>
                <div class="grid gap-3">
                    <Label for="asset">Asset</Label>
                    <Select v-model="holdingForm.asset">
                        <SelectTrigger class="w-full">
                            <SelectValue placeholder="Select asset" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem v-for="asset in assets" :key="asset.id" :value="asset.id">
                                {{ asset.name }}
                            </SelectItem>
                        </SelectContent>
                    </Select>
                </div>
                <div class="grid gap-3">
                    <Label for="amount">Amount</Label>
                    <Input v-model="holdingForm.amount" id="amount" name="amount" default-value="0" />
                </div>
                <div class="grid gap-3">
                    <Label for="stock">Stock</Label>
                    <Input v-model="holdingForm.stock" id="stock" name="amount" default-value="0" disabled/>
                </div>
            </div>
            <DialogFooter>
                <DialogClose as-child>
                    <Button variant="outline">
                        Cancel
                    </Button>
                </DialogClose>
                <Button type="button" @click.prevent="submit">
                    Save
                </Button>
            </DialogFooter>
        </DialogContent>
    </Dialog>
</template>
