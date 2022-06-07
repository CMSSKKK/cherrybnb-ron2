//
//  QueryParameter.swift
//  Cherrybnb
//
//  Created by Bumgeun Song on 2022/06/07.
//  Copyright © 2022 Codesquad. All rights reserved.
//

import Foundation

struct QueryParameter {
    var dateRange: (Date?, Date?)
    var place: Place?
    var priceRange: Range<Decimal>?
    var people: HeadCount?
}

struct HeadCount {
    let adults: Int
    let child: Int
    let infant: Int
}
