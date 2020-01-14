package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    GildedRose(Item item) {
        this(new Item[]{item});
    }

    // TODO bad method name (it updates quality, but also sellIn).
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                // Everything except "Aged Brie", "Backstage passes" and "Sulfuras" degrades by 1 daily (down until 0)
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                // Item is "Aged Brie" or "Backstage passes ..."
                if (items[i].quality < 50) {
                    // Their quality increases by +1 daily (up to 50)
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            // Backstage passes quality increase by +2 when 6-10 days before 'sell by' date
                            // TODO bug? according to spec update +2 if sellIn <=10, but sellIn hasn't been updated yet!
                            // So if at this line, sellIn == 10, immediately after it will be decreased to 9!
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            // Backstage passes quality increase by +3 on 6-10 days before 'sell by' date
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            // Each item's sellIn value decreases by 1 each day, except for "Sulfuras, Hand of Ragnaros"
            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                // Quality decreases twice as fast (2 instead of 1) once 'sell by' date has passed
                                // except for products "Aged Brie" and "Backstage passes ..." and "Sulfuras ..."
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        // "Backstage passes" quality drops to 0 after the concert
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    // Quality of Brie increases twice as fast (2 instead of 1) once 'sell by' date has passed
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}