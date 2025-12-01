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

import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Button } from '@/components/ui/button'
import { Plus } from "lucide-vue-next"
import { ref } from 'vue'
import { savePortfolio } from '@/services/portfolio'

defineProps<{
    isOpen: boolean
}>()

const emit = defineEmits<{
    (e: "update:open", value: boolean): void,
    (e: "reload-table", value: boolean): void,
}>();

const portfolio = ref({
    name: "",
});

const submitPortfolio = () => {
    savePortfolio(portfolio.value).then(() => {
        emit("update:open", false);
        emit("reload-table", true);
    });
}
</script>

<template>
    <Dialog :open="isOpen" @update:open="(val) => emit('update:open', val)">
        <DialogTrigger>
            <Button variant="outline" size="sm">
                <Plus />
            </Button>
        </DialogTrigger>
        <DialogContent>
            <DialogHeader>
                <DialogTitle>Portfolio</DialogTitle>
                <DialogDescription>Donec quis arcu condimentum, egestas elit tempus, varius sapien. </DialogDescription>
            </DialogHeader>
            <div class="grid gap-4">
                <div class="grid gap-3">
                    <Label for="name-1">Name</Label>
                    <Input v-model="portfolio.name" id="name-1" name="name" placeholder="High Risk High Reward" />
                </div>
            </div>
            <DialogFooter>
                <DialogClose as-child>
                    <Button variant="outline">
                        Cancel
                    </Button>
                </DialogClose>
                <Button type="button" @click.prevent="submitPortfolio">
                    Save
                </Button>
            </DialogFooter>
        </DialogContent>
    </Dialog>
</template>
